package com.spanfish.shop.service.impl;

import static com.spanfish.shop.model.entity.OrderStatus.PROCESSING;
import static com.spanfish.shop.model.entity.OrderStatus.findById;
import static com.spanfish.shop.specification.OrderSpec.witchNumber;
import static java.util.Calendar.getInstance;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.data.domain.PageRequest.of;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Cart;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.Order;
import com.spanfish.shop.model.entity.OrderProduct;
import com.spanfish.shop.repository.OrderRepository;
import com.spanfish.shop.service.CartService;
import com.spanfish.shop.service.CustomerService;
import com.spanfish.shop.service.OrderService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private static final int ALPHABETIC_COUNT = 1;
  private static final int NUMERIC_COUNT = 6;
  private static final int DEFAULT_ORDER_COUNT = 0;

  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final CustomerService customerService;

  @Override
  public Integer getAllOrdersCount() {
    final Customer customer = customerService.getCustomer();
    return orderRepository.countAllByCustomer(customer).orElse(DEFAULT_ORDER_COUNT);
  }

  @Override
  public Page<Order> findAllCustomerOrders(final Integer page, final Integer pageSize) {
    final Customer customer = customerService.getCustomer();
    return orderRepository.findAllByCustomerOrderByDateDesc(customer, of(page, pageSize));
  }

  @Override
  public Page<Order> findAllOrders(final String query, final Integer page, final Integer pageSize) {
    final Specification<Order> specification = witchNumber(query);
    return orderRepository.findAll(specification, of(page, pageSize));
  }

  @Override
  public Order saveOrder() {
    final Customer customer = customerService.getCustomer();
    final Cart cart = customer.getCart();

    final Order saveOrder =
        Order.builder()
            .customer(customer)
            .orderNumber(
                randomAlphabetic(ALPHABETIC_COUNT).toUpperCase() + randomNumeric(NUMERIC_COUNT))
            .orderProductList(new ArrayList<>())
            .date(getInstance().getTime())
            .totalPrice(cart.getTotalPrice())
            .build();

    cart.getCartItemList()
        .forEach(
            cartItem -> {
              final OrderProduct orderProduct =
                  OrderProduct.builder()
                      .order(saveOrder)
                      .product(cartItem.getProduct())
                      .amount(cartItem.getAmount())
                      .build();
              saveOrder.getOrderProductList().add(orderProduct);
            });

    saveOrder.setStatus(PROCESSING);

    final Order order = orderRepository.save(saveOrder);
    cartService.clearCart();
    return order;
  }

  @Override
  public void changeOrderStatus(final String code, final Integer status) {
    final Order order =
        orderRepository
            .findByOrderNumber(code)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Could not find any order by order number " + code));
    order.setStatus(findById(status));
    orderRepository.save(order);
  }
}
