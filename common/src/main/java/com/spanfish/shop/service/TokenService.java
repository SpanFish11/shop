package com.spanfish.shop.service;

import com.spanfish.shop.model.ResetPassword;
import com.spanfish.shop.model.entity.Customer;

public interface TokenService {

  void createVerificationEmailToken(Customer customer);

  void validateVerificationEmailToken(String token);

  void createPasswordResetToken(String email);

  void validatePasswordResetToken(String token);

  void passwordReset(ResetPassword resetPassword);
}
