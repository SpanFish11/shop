package com.spanfish.shop.model.request.customer;

import lombok.Data;

@Data
public class ForgotResetPasswordRequest {

  private String token;
  private String newPassword;
  private String newPasswordRepeat;
}
