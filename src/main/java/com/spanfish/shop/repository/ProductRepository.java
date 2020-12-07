package com.spanfish.shop.repository;

import com.spanfish.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findProductsByManufacturer_Id(Long manufacturerId, Pageable pageable);
}
