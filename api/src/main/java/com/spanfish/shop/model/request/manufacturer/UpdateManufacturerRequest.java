package com.spanfish.shop.model.request.manufacturer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateManufacturerRequest extends CreateManufacturerRequest {

  @NotNull
  @Min(1)
  private Long id;
}
