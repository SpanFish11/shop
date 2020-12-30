package com.spanfish.shop.security.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class AuthRequest {

  @NonNull @NotBlank @Email private String email;

  @NonNull @NotBlank private String password;
}
