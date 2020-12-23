package com.spanfish.shop.security.service;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.Role;
import com.spanfish.shop.service.CustomerService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
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
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Customer customer = customerService.findCustomerByEmailIgnoreCase(email);

    return new User(
        customer.getEmail(),
        customer.getPassword(),
        AuthorityUtils.commaSeparatedStringToAuthorityList(
            customer.getRoles().stream().map(Role::getRoleName).collect(Collectors.joining(","))));
  }
}
