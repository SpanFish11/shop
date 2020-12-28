package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Customer;
import com.spanfish.shop.model.request.customer.ForgotResetPasswordRequest;

public interface TokenService {

  void createVerificationEmailToken(Customer customer);

  void validateVerificationEmailToken(String token);

  void createPasswordResetToken(String email);

  void validatePasswordResetToken(String token);

  void passwordReset(ForgotResetPasswordRequest forgotResetPasswordRequest);
}
