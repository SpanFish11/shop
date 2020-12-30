package com.spanfish.shop.service.impl;

import static com.spanfish.shop.model.entity.SystemRoles.ROLE_USER;
import static java.lang.String.format;
import static java.util.List.of;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.Role;
import com.spanfish.shop.model.entity.SystemRoles;
import com.spanfish.shop.repository.CustomerRepository;
import com.spanfish.shop.repository.RoleRepository;
import com.spanfish.shop.service.CustomerService;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Page<Customer> getAll(final Pageable pageable) {
    return customerRepository.findAll(pageable);
  }

  @Override
  public Customer getCustomer() {
    final String customerEmail = getContext().getAuthentication().getName();
    if (StringUtils.isEmpty(customerEmail)) {
      throw new AccessDeniedException("Invalid access");
    }
    return findCustomerByEmailIgnoreCase(customerEmail);
  }

  @Override
  public Customer getById(final Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    format("Could not find any customer with the ID %d", id)));
  }

  @Override
  public Customer findCustomerByEmailIgnoreCase(final String email) {
    return customerRepository
        .findCustomerByEmailIgnoreCase(email)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Could not find any customer with the email " + email));
  }

  @Override
  public Customer save(final Customer customer) {
    customer.setPassword(passwordEncoder.encode(customer.getPassword()));
    customer.getContacts().setCustomer(customer);
    customer.setIsActive(true);
    customer.setRoles(new HashSet<>(of(getRole(ROLE_USER))));
    return customerRepository.save(customer);
  }

  @Override
  public Customer update(final Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void updateCustomer(final Customer customer) {
    customerRepository.save(customer);
  }

  @Override
  public void delete(final Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  public void resetPassword(final Customer customer) {
    customer.setPassword(passwordEncoder.encode(customer.getPassword()));
    customerRepository.save(customer);
  }

  @Override
  public void addRole(final Long id, final SystemRoles systemRoles) {
    final Customer customer = getById(id);
    final Role role = getRole(systemRoles);

    if (customer.getRoles().contains(role)) {
      return;
    }

    customer.getRoles().add(role);
    customer.setRoles(customer.getRoles());
    customerRepository.save(customer);
  }

  @Override
  public void removeRole(final Long id, final SystemRoles systemRoles) {
    final Customer customer = getById(id);
    final Role role = getRole(systemRoles);

    if (role.getRoleName().equals(ROLE_USER.name()) || !customer.getRoles().contains(role)) {
      return;
    }

    customer.getRoles().remove(role);
    customer.setRoles(customer.getRoles());
    customerRepository.save(customer);
  }

  @Override
  public Boolean existsByEmail(final String email) {
    return customerRepository.existsByEmail(email);
  }

  private Role getRole(final SystemRoles systemRoles) {
    return roleRepository
        .findRoleByRoleName(systemRoles.name())
        .orElseThrow(
            () -> new ResourceNotFoundException("Could not find any role " + systemRoles.name()));
  }
}
