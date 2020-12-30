package com.spanfish.shop.model.request.customer;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequest {

  @NotBlank(message = "Token is mandatory")
  private String token;
}
