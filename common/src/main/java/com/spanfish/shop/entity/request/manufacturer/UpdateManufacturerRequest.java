package com.spanfish.shop.entity.request.manufacturer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

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
