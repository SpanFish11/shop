package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.Role;
import com.spanfish.shop.entity.request.customer.ResetPasswordRequest;
import com.spanfish.shop.entity.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.entity.request.customer.UpdateCustomerAddressRequest;
import com.spanfish.shop.entity.request.customer.UpdateCustomerRequest;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.mapper.CustomerMapper;
import com.spanfish.shop.repository.CustomerRepository;
import com.spanfish.shop.repository.RoleRepository;
import com.spanfish.shop.service.CustomerService;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final RoleRepository roleRepository;
  private final CustomerMapper customerMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Page<Customer> getAll(Pageable pageable) {
    return customerRepository.findAll(pageable);
  }

  @Override
  public Customer getCustomer() {

    String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    if (StringUtils.isEmpty(customerEmail)) {
      throw new AccessDeniedException("Invalid access");
    }
    Optional<Customer> optionalCustomer =
        customerRepository.findCustomerByEmailIgnoreCase(customerEmail);
    if (optionalCustomer.isEmpty()) {
      throw new ResourceNotFoundException(
          "Could not find any customers with the email " + customerEmail);
    }
    return optionalCustomer.get();
  }

  @Override
  public Customer getById(Long id) {

    Optional<Customer> optionalCustomer = customerRepository.findById(id);
    if (optionalCustomer.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format("Could not find any customer with the ID %d", id));
    }
    return optionalCustomer.get();
  }

  @Override
  public Customer findCustomerByEmailIgnoreCase(String email) {

    Optional<Customer> optionalCustomer = customerRepository.findCustomerByEmailIgnoreCase(email);
    if (optionalCustomer.isEmpty()) {
      throw new ResourceNotFoundException("Could not find any customers with the email " + email);
    }
    return optionalCustomer.get();
  }

  @Override
  public Customer save(RegisterCustomerRequest registerCustomerRequest) {

    if (existsByEmail(registerCustomerRequest.getEmail())) {
      throw new InvalidArgumentException("An account already exists with this email");
    }

    Customer newCustomer = new Customer();
    newCustomer.setName(registerCustomerRequest.getName());
    newCustomer.setEmail(registerCustomerRequest.getEmail());
    newCustomer.setPassword(passwordEncoder.encode(registerCustomerRequest.getPassword()));
    newCustomer.setRegistrationDate(Calendar.getInstance().getTime());
    newCustomer.getContacts().setCustomer(newCustomer);

    Optional<Role> role = roleRepository.findRoleByRoleName("ROLE_USER");
    role.ifPresent(value -> newCustomer.setRoles(new HashSet<>(List.of(value))));

    return customerRepository.save(newCustomer);
  }

  @Override
  public Customer update(UpdateCustomerRequest updateCustomerRequest) {

    Customer customer = getCustomer();
    customer.setName(updateCustomerRequest.getName());
    customer.getContacts().setPhone(updateCustomerRequest.getPhone());
    return customerRepository.save(customer);
  }

  @Override
  public Customer update(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateAddress(UpdateCustomerAddressRequest updateCustomerAddressRequest) {

    Customer customer = getCustomer();
    customer.getContacts().setAddress(updateCustomerAddressRequest.getAddress());
    customer.getContacts().setCity(updateCustomerAddressRequest.getCity());
    customer.getContacts().setZip(updateCustomerAddressRequest.getZip());
    return customerRepository.save(customer);
  }

  @Override
  public void delete(Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

    Customer customer = getCustomer();

    if (!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), customer.getPassword())) {
      throw new InvalidArgumentException("Invalid password");
    }

    customer.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
    customerRepository.save(customer);
  }

  private Boolean existsByEmail(String email) {
    return customerRepository.existsByEmail(email);
  }
}
