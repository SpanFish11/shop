package com.spanfish.shop.model.request.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

  @NotNull
  @NotBlank(message = "Email is mandatory")
  private String email;
}
