package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Cart;
import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.Order;
import com.spanfish.shop.entity.OrderProduct;
import com.spanfish.shop.repository.OrderRepository;
import com.spanfish.shop.service.CartService;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.OrderService;
import java.util.ArrayList;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CartService cartService;
  // TODO UserService
  private final CustomerService customerService;

  @Override
  public Page<Order> findAllCustomerOrders(Pageable pageable) {
    // TODO UserService
    Customer customer = customerService.getById(1L).get();
    return orderRepository.findAllByCustomerOrderByDateDesc(customer, pageable);
  }

  @Override
  public Page<Order> findAllOrders(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }

  @Override
  public Order saveOrder() {
    // TODO UserService
    Customer customer = customerService.getById(1L).get();
    Cart cart = customer.getCart();

    Order saveOrder =
        Order.builder()
            .customer(customer)
            .orderProductList(new ArrayList<>())
            .date(Calendar.getInstance().getTime())
            .totalPrice(cart.getTotalPrice())
            .deliveryIncluded(false)
            .confirmed(false)
            .build();

    cart.getCartItemList()
        .forEach(
            cartItem -> {
              OrderProduct orderProduct =
                  OrderProduct.builder()
                      .order(saveOrder)
                      .product(cartItem.getProduct())
                      .amount(cartItem.getAmount())
                      .build();
              saveOrder.getOrderProductList().add(orderProduct);
            });

    Order order = orderRepository.save(saveOrder);
    cartService.clearCart();
    return order;
  }
}
