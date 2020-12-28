package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.request.product.CreateProductRequest;
import com.spanfish.shop.model.request.product.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

  Product findById(Long productId);

  Page<Product> findAll(Pageable pageable);

  Page<Product> findAllManufacturersProducts(Long manufacturerId, Pageable pageable);

  Page<Product> findAllCategoryProducts(Long categoryId, Pageable pageable);

  Page<Product> findAllSubCategoryProducts(Long subCategoryId, Pageable pageable);

  Product create(CreateProductRequest createProductRequest);

  Product update(UpdateProductRequest updateProductRequest);

  Product addProduct(String json, MultipartFile photo);

  Product updateProduct(String json, MultipartFile photo);

  void delete(Long productId);

  Boolean existsById(Long id);
}
