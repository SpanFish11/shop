package com.spanfish.shop.model.request.subcategory;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateSubCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;

  @NotNull
  @Min(1)
  private Long categoryId;

  @NotNull
  private String name;
}
