package com.spanfish.shop.model.request.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryRequest {

  @NotNull
  @NotBlank(message = "Category name is mandatory")
  private String name;
}
