package com.spanfish.shop.service.impl;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.RegisterCustomerRequest;
import com.spanfish.shop.model.request.customer.ResetPasswordRequest;
import com.spanfish.shop.model.request.customer.UpdateAddressRequest;
import com.spanfish.shop.model.request.customer.UpdateCustomerRequest;
import com.spanfish.shop.repository.CustomerRepository;
import com.spanfish.shop.repository.RoleRepository;
import com.spanfish.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final RoleRepository roleRepository;
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
    return findCustomerByEmailIgnoreCase(customerEmail);
  }

  @Override
  public Customer getById(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format("Could not find any customer with the ID %d", id)));
  }

  @Override
  public Customer findCustomerByEmailIgnoreCase(String email) {
    return customerRepository
        .findCustomerByEmailIgnoreCase(email)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Could not find any customer with the email " + email));
  }

  @Override
  public Customer save(RegisterCustomerRequest request) {

    if (existsByEmail(request.getEmail())) {
      throw new InvalidArgumentException(
          "An account already exists with this email: " + request.getEmail());
    }

    Customer customer = new Customer();
    customer.setName(request.getName());
    customer.setEmail(request.getEmail());
    customer.setPassword(passwordEncoder.encode(request.getPassword()));
    customer.setRegistrationDate(Calendar.getInstance().getTime());
    customer.getContacts().setCustomer(customer);
    customer.setIsActive(true);

    roleRepository
        .findRoleByRoleName("ROLE_USER")
        .ifPresent(role -> customer.setRoles(new HashSet<>(List.of(role))));

    return customerRepository.save(customer);
  }

  @Override
  public Customer update(UpdateCustomerRequest updateCustomerRequest) {
    Customer customer = getCustomer();
    customer.setName(updateCustomerRequest.getName());
    customer.getContacts().setPhone(updateCustomerRequest.getPhone());
    return customerRepository.save(customer);
  }

  @Override
  public void updateCustomer(Customer customer) {
    customerRepository.save(customer);
  }

  @Override
  public Customer updateAddress(UpdateAddressRequest updateAddressRequest) {
    Customer customer = getCustomer();
    customer.getContacts().setAddress(updateAddressRequest.getAddress());
    customer.getContacts().setCity(updateAddressRequest.getCity());
    customer.getContacts().setZip(updateAddressRequest.getZip());
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
