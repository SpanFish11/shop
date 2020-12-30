package com.spanfish.shop.model.request.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateCustomerRequest {

  @NonNull
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 50)
  private String name;

  @NonNull
  @NotBlank(message = "Phone number is mandatory")
  @Pattern(regexp = "^\\d{12}$")
  @Size(min = 12, max = 13)
  private String phone;
}
