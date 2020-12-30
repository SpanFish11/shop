package com.spanfish.shop.model.request.customer;

import com.spanfish.shop.validator.PasswordValueMatch;
import com.spanfish.shop.validator.ValidPassword;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@PasswordValueMatch.List({
  @PasswordValueMatch(
      field = "newPassword",
      fieldMatch = "newPasswordRepeat",
      message = "Passwords do not match!")
})
@Data
public class ForgotResetPasswordRequest {

  @NonNull @NotBlank private String token;

  @NonNull
  @NotBlank(message = "Password is mandatory")
  @ValidPassword
  private String newPassword;

  @NonNull
  @NotBlank(message = "Confirm Password is mandatory")
  private String newPasswordRepeat;
}
