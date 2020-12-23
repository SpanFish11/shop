package com.spanfish.shop.entity.request.category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;

  @NotNull
  private String name;
}
