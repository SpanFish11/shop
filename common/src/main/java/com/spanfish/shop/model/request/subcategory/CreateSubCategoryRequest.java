package com.spanfish.shop.model.request.subcategory;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateSubCategoryRequest {

  @NotNull
  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotNull
  @Min(1)
  private Long categoryId;
}
