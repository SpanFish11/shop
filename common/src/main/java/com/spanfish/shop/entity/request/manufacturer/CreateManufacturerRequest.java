package com.spanfish.shop.entity.request.manufacturer;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateManufacturerRequest {

  @NotNull
  private String name;

  @NotNull
  private String description;
}
