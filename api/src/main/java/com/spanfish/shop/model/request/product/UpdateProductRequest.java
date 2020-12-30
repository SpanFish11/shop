package com.spanfish.shop.model.request.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateProductRequest extends CreateProductRequest {

  @NotNull
  @Min(1)
  private Long id;

  @NotNull @NotBlank private String code;

  @NotNull @NotBlank private String picUrl;
}
