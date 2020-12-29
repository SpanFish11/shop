package com.spanfish.shop.model.request.customer;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateCustomerRequest {

  @NonNull
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 50)
  private String name;

  @NonNull
  @NotBlank(message = "Phone number is mandatory")
  private String phone;
}
