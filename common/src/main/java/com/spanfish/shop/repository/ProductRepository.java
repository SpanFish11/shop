package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findProductsByManufacturer_Id(Long manufacturerId, Pageable pageable);

  Page<Product> findProductsByCategory_Id(Long categoryId, Pageable pageable);

  Page<Product> findProductsBySubCategory_Id(Long subCategoryId, Pageable pageable);
}
