package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findCustomerByEmailIgnoreCase(String email);

  Boolean existsByEmail(String email);
}
