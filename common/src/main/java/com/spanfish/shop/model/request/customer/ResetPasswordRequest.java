package com.spanfish.shop.model.request.customer;

import com.spanfish.shop.validator.PasswordValueMatch;
import com.spanfish.shop.validator.ValidPassword;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@PasswordValueMatch.List({
  @PasswordValueMatch(
      field = "newPassword",
      fieldMatch = "newPasswordConfirm",
      message = "Passwords do not match!")
})
@Data
public class ResetPasswordRequest {

  @NonNull
  @NotBlank(message = "Old Password is mandatory")
  private String oldPassword;

  @NonNull
  @NotBlank(message = "New Password is mandatory")
  @ValidPassword
  private String newPassword;

  @NonNull
  @NotBlank(message = "New Confirm Password is mandatory")
  private String newPasswordConfirm;
}
