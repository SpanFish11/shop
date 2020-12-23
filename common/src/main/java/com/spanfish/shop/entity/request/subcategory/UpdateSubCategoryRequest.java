package com.spanfish.shop.entity.request.subcategory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSubCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;

  @NotNull
  @Min(1)
  private long categoryId;

  @NotNull
  private String name;
}
