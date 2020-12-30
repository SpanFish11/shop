package com.spanfish.shop.model.request.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateAddressRequest {

  @NonNull
  @NotBlank(message = "City is mandatory")
  @Size(min = 3, max = 100)
  private String city;

  @NotNull
  @Size(min = 6, max = 6)
  @Pattern(regexp = "^[0-9]*$")
  private Integer zip;

  @NonNull
  @NotBlank(message = "Address is mandatory")
  private String address;
}
