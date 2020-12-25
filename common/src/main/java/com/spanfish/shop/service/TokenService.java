package com.spanfish.shop.service;

import com.spanfish.shop.entity.Customer;
import com.spanfish.shop.entity.request.customer.ForgotResetPasswordRequest;

public interface TokenService {

  void createVerificationEmailToken(Customer customer);

  void validateVerificationEmailToken(String token);

  void createPasswordResetToken(String email);

  void validatePasswordResetToken(String token);

  void passwordReset(ForgotResetPasswordRequest forgotResetPasswordRequest);
}
