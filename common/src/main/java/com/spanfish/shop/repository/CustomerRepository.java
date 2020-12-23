package com.spanfish.shop.repository;

import com.spanfish.shop.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findCustomerByEmailIgnoreCase(String email);
}