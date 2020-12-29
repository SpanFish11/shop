package com.spanfish.shop.model.request.manufacturer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateManufacturerRequest {

  @NotNull
  @NotBlank(message = "Name is mandatory")
  private String name;

  @Size(max = 1000)
  private String description;
}
