package com.spanfish.shop.model.request.manufacturer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateManufacturerRequest {

  @NotNull
  @NotBlank(message = "Name is mandatory")
  private String name;
}
