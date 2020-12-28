package com.spanfish.shop.model.request.manufacturer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateManufacturerRequest {

  @NotNull
  private String name;

  @NotNull
  private String description;
}
