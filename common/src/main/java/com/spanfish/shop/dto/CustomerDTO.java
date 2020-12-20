package com.spanfish.shop.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  @Pattern(regexp = "^[a-zA-Z\\s]+$")
  @Size(min = 3, max = 26)
  private String customerName;

  @NotEmpty
  @Pattern(
      regexp =
          "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$")
  @Size(min = 3, max = 52)
  private String customerEmail;

  @NotEmpty
  @Size(min = 6, max = 52)
  private String customerPassword;
}
