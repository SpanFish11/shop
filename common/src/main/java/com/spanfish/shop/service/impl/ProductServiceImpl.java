package com.spanfish.shop.service.impl;

import static com.spanfish.shop.specification.ProductSpec.maxPrice;
import static com.spanfish.shop.specification.ProductSpec.minPrice;
import static com.spanfish.shop.specification.ProductSpec.withCategory;
import static com.spanfish.shop.specification.ProductSpec.withManufacturer;
import static com.spanfish.shop.specification.ProductSpec.withSubCategory;
import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.jpa.domain.Specification.where;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Category;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.entity.SubCategory;
import com.spanfish.shop.repository.ProductRepository;
import com.spanfish.shop.service.AmazonService;
import com.spanfish.shop.service.CategoryService;
import com.spanfish.shop.service.ManufacturerService;
import com.spanfish.shop.service.ProductService;
import com.spanfish.shop.service.SubcategoryService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

  protected static final String DEFAULT_IMAGE_URL =
      "https://spanfishbucket.s3.eu-central-1.amazonaws.com/products/No_image_available.svg.png";
  private static final String EXCEPTION_MESSAGE = "Could not find any product with the ID %d";
  private static final int CODE_SIZE = 7;

  private final ProductRepository productRepository;
  private final AmazonService amazonService;
  private final ManufacturerService manufacturerService;
  private final CategoryService categoryService;
  private final SubcategoryService subcategoryService;

  @Override
  @Cacheable(key = "#id")
  public Product findById(final Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id)));
  }

  @Override
  @Cacheable(keyGenerator = "customKeyGenerator", unless = "#result.totalElements == 0")
  public Page<Product> findAll(
      final Integer page,
      final Integer pageSize,
      final String sort,
      final Long manufacturerId,
      final Long categoryId,
      final Long subcategoryId,
      final Double minPrice,
      final Double maxPrice) {
    PageRequest pageRequest;
    if (nonNull(sort) && !sort.isBlank()) {
      final Sort sortRequest = getSort(sort);
      if (isNull(sortRequest)) {
        throw new InvalidArgumentException("Invalid sort parameter");
      }
      pageRequest = of(page, pageSize, sortRequest);
    } else {
      pageRequest = of(page, pageSize);
    }

    final Specification<Product> specifications =
        where(
            minPrice(valueOf(minPrice))
                .and(maxPrice(valueOf(maxPrice)))
                .and(withManufacturer(manufacturerId))
                .and(withCategory(categoryId))
                .and(withSubCategory(subcategoryId)));

    return productRepository.findAll(specifications, pageRequest);
  }

  @Override
  @Cacheable(keyGenerator = "customKeyGenerator", unless = "#result.totalElements == 0")
  public Page<Product> search(final String query, final Integer page, final Integer pageSize) {
    return productRepository.findByNameContainsIgnoreCase(query, of(page, pageSize));
  }

  @Override
  @Cacheable(keyGenerator = "customKeyGenerator", unless = "#result.totalElements == 0")
  public Page<Product> findAllManufacturersProducts(
      final Long manufacturerId, final Integer page, final Integer pageSize) {
    final Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
    return productRepository.findProductsByManufacturer(manufacturer, of(page, pageSize));
  }

  @Override
  @Cacheable(keyGenerator = "customKeyGenerator", unless = "#result.totalElements == 0")
  public Page<Product> findAllCategoryProducts(
      final Long categoryId, final Integer page, final Integer pageSize) {
    final Category category = categoryService.getById(categoryId);
    return productRepository.findProductsByCategory(category, of(page, pageSize));
  }

  @Override
  @Cacheable(keyGenerator = "customKeyGenerator", unless = "#result.totalElements == 0")
  public Page<Product> findAllSubCategoryProducts(
      final Long subCategoryId, final Integer page, final Integer pageSize) {
    final SubCategory subCategory = subcategoryService.getById(subCategoryId);
    return productRepository.findProductsBySubCategory(subCategory, of(page, pageSize));
  }

  @Override
  public Product create(final Product product) {
    final String code = randomNumeric(CODE_SIZE);
    product.setCode(code);
    product.setPictureUrl(DEFAULT_IMAGE_URL);
    return productRepository.save(product);
  }

  @Override
  @CachePut(key = "#product.id")
  public Product update(final Product product) {
    return productRepository.save(product);
  }

  @Override
  @CachePut(key = "#id")
  public Product updateProductPhoto(final Long id, final MultipartFile photo) throws IOException {
    final byte[] imageBytes = photo.getBytes();
    final Product product = findById(id);
    product.setPictureUrl(amazonService.uploadImage(imageBytes, id));
    return productRepository.save(product);
  }

  @Override
  @CacheEvict(key = "#productId", allEntries = true)
  public void delete(final Long productId) {
    if (existsById(productId)) {
      productRepository.deleteById(productId);
    }
  }

  @Override
  public Boolean existsById(final Long id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id));
    }
    return true;
  }

  private Sort getSort(final String sort) {
    switch (sort) {
      case "lowest":
        return by(ASC);
      case "highest":
        return by(DESC);
      default:
        return null;
    }
  }
}
