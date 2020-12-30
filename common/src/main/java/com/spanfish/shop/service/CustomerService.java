package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.SystemRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  Page<Customer> getAll(Pageable pageable);

  Customer getCustomer();

  Customer getById(Long id);

  Customer findCustomerByEmailIgnoreCase(String email);

  Customer save(Customer customer);

  Customer update(Customer customer);

  void updateCustomer(Customer customer);

  void delete(Long id);

  void resetPassword(Customer customer);

  void addRole(Long id, SystemRoles systemRoles);

  void removeRole(Long id, SystemRoles systemRoles);

  Boolean existsByEmail(String email);
}
