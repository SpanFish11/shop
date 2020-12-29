package com.spanfish.shop.model.request.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateProductRequest extends CreateProductRequest {

  @NotNull
  @Min(1)
  private Long id;
}