package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Product;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

  Product findById(Long productId);

  Page<Product> findAll(
      Integer page,
      Integer pageSize,
      String sort,
      Long manufacturerId,
      Long categoryId,
      Long subcategoryId,
      Double minPrice,
      Double maxPrice);

  Page<Product> search(String query, Integer page, Integer pageSize);

  Page<Product> findAllManufacturersProducts(Long manufacturerId, Integer page, Integer pageSize);

  Page<Product> findAllCategoryProducts(Long categoryId, Integer page, Integer pageSize);

  Page<Product> findAllSubCategoryProducts(Long subCategoryId, Integer page, Integer pageSize);

  Product create(Product product);

  Product update(Product product);

  Product updateProductPhoto(Long id, MultipartFile photo) throws IOException;

  void delete(Long productId);

  Boolean existsById(Long id);
}
