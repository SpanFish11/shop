package com.spanfish.shop.model.request.subcategory;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSubCategoryRequest extends CreateSubCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;
}
