package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {}
