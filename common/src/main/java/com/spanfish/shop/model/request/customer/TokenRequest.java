package com.spanfish.shop.model.request.customer;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRequest {

  @NonNull
  @NotBlank(message = "Token is mandatory")
  private String token;
}
