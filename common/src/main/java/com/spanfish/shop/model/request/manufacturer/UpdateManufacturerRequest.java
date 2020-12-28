package com.spanfish.shop.model.request.manufacturer;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateManufacturerRequest {

  @NotNull
  @Min(1)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String description;
}
