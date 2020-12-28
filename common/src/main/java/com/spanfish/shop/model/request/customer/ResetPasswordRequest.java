package com.spanfish.shop.model.request.customer;

import lombok.Data;

@Data
public class ResetPasswordRequest {

  private String oldPassword;
  private String newPassword;
  private String newPasswordConfirm;
}
