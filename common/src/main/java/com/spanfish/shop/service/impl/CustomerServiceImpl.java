package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.Role;
import com.spanfish.shop.repository.CustomerRepository;
import com.spanfish.shop.repository.RoleRepository;
import com.spanfish.shop.service.CustomerService;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final RoleRepository roleRepository;

  @Override
  public Optional<Customer> getById(Long id) {
    return customerRepository.findById(id);
  }

  @Override
  public Page<Customer> getAll(Pageable pageable) {
    return customerRepository.findAll(pageable);
  }

  @Override
  public Customer getCustomer() {
    String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    if (Objects.isNull(customerEmail)) {
      throw new AccessDeniedException("Invalid access");
    }

    Optional<Customer> optionalCustomer =
        customerRepository.findCustomerByEmailIgnoreCase(customerEmail);
    if (optionalCustomer.isEmpty()) {
      throw new UsernameNotFoundException(
          String.format("No user found with email '%s'.", customerEmail));
    }

    return optionalCustomer.get();
  }

  @Override
  public Customer save(Customer customer) {
    customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
    Optional<Role> role = roleRepository.findRoleByRoleName("ROLE_USER");
    role.ifPresent(value -> customer.setRoles(new HashSet<>(List.of(value))));
    return customerRepository.save(customer);
  }

  @Override
  public Customer update(Customer requestCustomer) {
    return customerRepository.save(requestCustomer);
  }

  @Override
  public void delete(Long id) {
    customerRepository.deleteById(id);
  }
}
