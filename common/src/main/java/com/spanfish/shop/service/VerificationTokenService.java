package com.spanfish.shop.service;

import com.spanfish.shop.entity.Customer;

public interface VerificationTokenService {

  void createOrUpdateToken(Customer customer);

  void validateToken(String token);
}
