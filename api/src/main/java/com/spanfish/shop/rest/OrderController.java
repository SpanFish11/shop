package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Order;
import com.spanfish.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<Page<Order>> getAllCustomerOrders(Pageable pageable) {
    return new ResponseEntity<>(orderService.findAllCustomerOrders(pageable), HttpStatus.OK);
  }

  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @GetMapping("/all")
  public ResponseEntity<Page<Order>> getAllOrders(Pageable pageable) {
    return new ResponseEntity<>(orderService.findAllOrders(pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Order> makeOrder() {
    return new ResponseEntity<>(orderService.saveOrder(), HttpStatus.OK);
  }
}
