package com.spanfish.shop.security.service;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.Role;
import com.spanfish.shop.repository.CustomerRepository;
import java.util.Optional;
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

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<Customer> optionalCustomer = customerRepository.findCustomerByEmailIgnoreCase(email);
    if (optionalCustomer.isEmpty()) {
      throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
    }

    return new User(
        optionalCustomer.get().getEmail(),
        optionalCustomer.get().getPassword(),
        AuthorityUtils.commaSeparatedStringToAuthorityList(
            optionalCustomer.get().getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(","))));
  }
}
