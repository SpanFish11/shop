package com.spanfish.shop.model.request.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateProductRequest {

  @NonNull
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 50)
  private String name;

  // pattern(10.2)
  @DecimalMin("1")
  private BigDecimal price;

  @Size(max = 1000)
  private String description;

  @NotNull
  @Min(1)
  private Long manufacturerId;

  @NotNull
  @Min(1)
  private Long categoryId;

  @NotNull
  @Min(1)
  private Long subcategoryId;
}
