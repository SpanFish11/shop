package com.spanfish.shop.entity.request.category;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequest {

  @NotNull
  private String name;
}
