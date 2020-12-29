package com.spanfish.shop.model.request.customer;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateAddressRequest {

  @NonNull
  @NotBlank(message = "City is mandatory")
  private String city;

  @NotNull
  @Size(min = 6, max = 6)
  private Integer zip;

  @NonNull
  @NotBlank(message = "Address is mandatory")
  private String address;
}
