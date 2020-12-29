package com.spanfish.shop.model.request.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateCategoryRequest {

  @NotNull
  @NotBlank(message = "Category name is mandatory")
  private String name;
}
