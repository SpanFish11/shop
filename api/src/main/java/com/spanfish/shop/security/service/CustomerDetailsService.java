package com.spanfish.shop.security.service;

import static java.util.stream.Collectors.joining;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.entity.Role;
import com.spanfish.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

  private final CustomerService customerService;

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

    final Customer customer = customerService.findCustomerByEmailIgnoreCase(email);

    return new User(
        customer.getEmail(),
        customer.getPassword(),
        commaSeparatedStringToAuthorityList(
            customer.getRoles().stream().map(Role::getRoleName).collect(joining(","))));
  }
}
