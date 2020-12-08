package com.spanfish.shop.service;

import com.spanfish.shop.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Optional<Product> findById(Long productId);

  Page<Product> findAll(Pageable pageable);

  Page<Product> findAllManufacturersProducts(Long manufacturerId, Pageable pageable);

  Page<Product> findAllCategoryProducts(Long categoryId, Pageable pageable);

  Product create(Product product);

  Product update(Product product);

  void delete(Long productId);
}
