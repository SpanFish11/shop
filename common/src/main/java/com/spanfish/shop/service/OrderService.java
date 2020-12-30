package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

  Integer getAllOrdersCount();

  Page<Order> findAllCustomerOrders(Integer page, Integer pageSize);

  Page<Order> findAllOrders(String number, Integer page, Integer pageSize);

  Order saveOrder();

  void changeOrderStatus(String code, Integer status);
}
