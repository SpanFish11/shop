package com.spanfish.shop.service;

import com.spanfish.shop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  Page<Order> findAllCustomerOrders(Pageable pageable);

  Page<Order> findAllOrders(Pageable pageable);

  Order saveOrder();
}
