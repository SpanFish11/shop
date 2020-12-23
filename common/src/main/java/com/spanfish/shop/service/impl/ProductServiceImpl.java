package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Product;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.repository.ProductRepository;
import com.spanfish.shop.service.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Optional<Product> findById(Long productId) {
    return productRepository.findById(productId);
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public Page<Product> findAllManufacturersProducts(Long manufacturerId, Pageable pageable) {

    Page<Product> products =
        productRepository.findProductsByManufacturer_Id(manufacturerId, pageable);
    if (products.getTotalElements() == 0) {
      throw new ResourceNotFoundException(
          String.format("Could not find products for manufacturer ID %d", manufacturerId));
    }
    return products;
  }

  @Override
  public Page<Product> findAllCategoryProducts(Long categoryId, Pageable pageable) {

    Page<Product> products = productRepository.findProductsByCategory_Id(categoryId, pageable);
    if (products.getTotalElements() == 0) {
      throw new ResourceNotFoundException(
          String.format("Could not find products for category ID %d", categoryId));
    }
    return products;
  }

  @Override
  public Page<Product> findAllSubCategoryProducts(Long subCategoryId, Pageable pageable) {

    Page<Product> products =
        productRepository.findProductsBySubCategory_Id(subCategoryId, pageable);
    if (products.getTotalElements() == 0) {
      throw new ResourceNotFoundException(
          String.format("Could not find products for subcategory ID %d", subCategoryId));
    }
    return products;
  }

  @Override
  public Product create(Product product) {
    product.setCode(generateRandomCode());
    return productRepository.save(product);
  }

  @Override
  public Product update(Product product) {
    return productRepository.save(product);
  }

  @Override
  public void delete(Long productId) {
    productRepository.deleteById(productId);
  }

  private String generateRandomCode() {
    return RandomStringUtils.randomNumeric(7);
  }
}
