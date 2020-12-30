package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Category;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  Page<Product> findProductsByManufacturer(Manufacturer manufacturer, Pageable pageable);

  Page<Product> findProductsByCategory(Category category, Pageable pageable);

  Page<Product> findProductsBySubCategory(SubCategory subCategory, Pageable pageable);

  Page<Product> findByNameContainsIgnoreCase(String query, Pageable pageable);
}
