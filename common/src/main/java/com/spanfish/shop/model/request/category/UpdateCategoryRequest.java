package com.spanfish.shop.model.request.category;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCategoryRequest extends CreateCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;
}
