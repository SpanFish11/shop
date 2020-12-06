package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.repository.CustomerRepository;
import com.spanfish.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public Optional<Customer> getById(Long id) {
    return customerRepository.findById(id);
  }

  @Override
  public Page<Customer> getAll(Pageable pageable) {
    return customerRepository.findAll(pageable);
  }

  @Override
  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  // TODO доделать метод
  @Override
  public Customer update(Customer requestCustomer) {
    return customerRepository.save(requestCustomer);
  }

  @Override
  public void delete(Long id) {
    customerRepository.deleteById(id);
  }
}
