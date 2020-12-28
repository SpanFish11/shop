package com.spanfish.shop.model.request.category;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;

  @NotNull
  private String name;
}
