package com.spanfish.shop.model.request.category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCategoryRequest extends CreateCategoryRequest {

  @NotNull
  @Min(1)
  private Long id;
}
