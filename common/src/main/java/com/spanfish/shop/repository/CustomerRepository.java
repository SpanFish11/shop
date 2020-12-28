package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findCustomerByEmailIgnoreCase(String email);

  Boolean existsByEmail(String email);
}
