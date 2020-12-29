package com.spanfish.shop.model.request.customer;

import com.spanfish.shop.validator.PasswordValueMatch;
import com.spanfish.shop.validator.ValidPassword;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordValueMatch.List({
  @PasswordValueMatch(
      field = "password",
      fieldMatch = "confirmPassword",
      message = "Passwords do not match!")
})
@Data
public class RegisterCustomerRequest {

  @NonNull
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 50)
  private String name;

  @NotNull
  @NotBlank(message = "Email is mandatory")
  private String email;

  @NonNull
  @NotBlank(message = "Password is mandatory")
  @ValidPassword
  private String password;

  @NonNull
  @NotBlank(message = "Confirm Password is mandatory")
  private String confirmPassword;
}
