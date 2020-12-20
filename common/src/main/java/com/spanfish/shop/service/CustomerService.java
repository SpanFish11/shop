package com.spanfish.shop.service;

import com.spanfish.shop.entity.Customer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  Page<Customer> getAll(Pageable pageable);

  Customer getCustomer();

  Optional<Customer> getById(Long id);

  Customer save(Customer customer);

  Customer update(Customer customer);

  void delete(Long id);
}
