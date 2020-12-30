package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository
    extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

  Optional<Integer> countAllByCustomer(Customer customer);

  Optional<Order> findByOrderNumber(String number);

  Page<Order> findAllByCustomerOrderByDateDesc(Customer customer, Pageable pageable);
}
