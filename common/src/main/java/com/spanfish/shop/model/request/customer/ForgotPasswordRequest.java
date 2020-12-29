package com.spanfish.shop.model.request.customer;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ForgotPasswordRequest {

  @NotNull
  @NotBlank(message = "Email is mandatory")
  private String email;
}
