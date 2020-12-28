package com.spanfish.shop.service.impl;

import com.google.gson.Gson;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.request.product.CreateProductRequest;
import com.spanfish.shop.model.request.product.UpdateProductRequest;
import com.spanfish.shop.repository.ProductRepository;
import com.spanfish.shop.repository.SubcategoryRepository;
import com.spanfish.shop.service.AWSS3Service;
import com.spanfish.shop.service.CategoryService;
import com.spanfish.shop.service.ManufacturerService;
import com.spanfish.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final SubcategoryRepository subcategoryRepository;
  private final CategoryService categoryService;
  private final ManufacturerService manufacturerService;
  private final AWSS3Service awss3Service;

  @Override
  @Cacheable(key = "{#root.methodName, #id}")
  public Product findById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format("Could not find any product with the ID %d", id)));
  }

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size()== 0")
  public Page<Product> findAll(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public Page<Product> findAllManufacturersProducts(Long manufacturerId, Pageable pageable) {
    Page<Product> products =
        productRepository.findProductsByManufacturer_Id(manufacturerId, pageable);
    if (products.getTotalElements() == 0) {
      throw new ResourceNotFoundException(
          String.format("Could not find any products for manufacturer ID %d", manufacturerId));
    }
    return products;
  }

  @Override
  public Page<Product> findAllCategoryProducts(Long categoryId, Pageable pageable) {
    Page<Product> products = productRepository.findProductsByCategory_Id(categoryId, pageable);
    if (products.getTotalElements() == 0) {
      throw new ResourceNotFoundException(
          String.format("Could not find any products for category ID %d", categoryId));
    }
    return products;
  }

  @Override
  public Page<Product> findAllSubCategoryProducts(Long subCategoryId, Pageable pageable) {
    Page<Product> products =
        productRepository.findProductsBySubCategory_Id(subCategoryId, pageable);
    if (products.getTotalElements() == 0) {
      throw new ResourceNotFoundException(
          String.format("Could not find any products for subcategory ID %d", subCategoryId));
    }
    return products;
  }

  @Override
  public Product create(CreateProductRequest request) {
    Product product = createProduct(request);
    return productRepository.save(product);
  }

  @Override
  public Product update(UpdateProductRequest request) {
    Product product = updateProduct(request);
    return productRepository.save(product);
  }

  @Override
  public Product addProduct(String json, MultipartFile photo) {
    CreateProductRequest request = new Gson().fromJson(json, CreateProductRequest.class);
    Product product = createProduct(request);
    if (!photo.isEmpty()) {
      product.setPictureUrl(awss3Service.uploadImage(photo));
    }
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(String json, MultipartFile photo) {
    UpdateProductRequest request = new Gson().fromJson(json, UpdateProductRequest.class);
    System.out.println(request.getId());
    Product product = updateProduct(request);
    if (!photo.isEmpty()) {
      // TODO
      // awss3Service.deleteImage(product.getPictureUrl());
      product.setPictureUrl(awss3Service.uploadImage(photo));
    }
    return productRepository.save(product);
  }

  @Override
  public void delete(Long productId) {
    if (existsById(productId)) {
      productRepository.deleteById(productId);
    }
  }

  @Override
  public Boolean existsById(Long id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException(
          String.format("Could not find any product with the ID %d", id));
    }
    return true;
  }

  private Product createProduct(CreateProductRequest request) {
    Product product = new Product();
    product.setName(request.getName());
    product.setPrice(request.getPrice());
    product.setDescription(request.getDescription());
    product.setCode(RandomStringUtils.randomNumeric(7));
    product.setManufacturer(manufacturerService.findById(request.getManufacturerId()));
    product.setCategory(categoryService.getById(request.getCategoryId()));
    product.setSubCategory(subcategoryRepository.getOne(request.getSubcategoryId()));
    return product;
  }

  private Product updateProduct(UpdateProductRequest request) {
    Product product = findById(request.getId());
    product.setName(request.getName());
    product.setPrice(request.getPrice());
    product.setDescription(request.getDescription());
    product.setManufacturer(manufacturerService.findById(request.getManufacturerId()));
    product.setCategory(categoryService.getById(request.getCategoryId()));
    product.setSubCategory(subcategoryRepository.getOne(request.getSubcategoryId()));
    return product;
  }
}
