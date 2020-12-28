package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Page<Order> findAllByCustomerOrderByDateDesc(Customer customer, Pageable pageable);
}
