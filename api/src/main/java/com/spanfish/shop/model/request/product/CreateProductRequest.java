package com.spanfish.shop.model.request.product;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CreateProductRequest {

  @NonNull
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 50)
  private String name;

  @DecimalMin("0.00")
  @Digits(integer = 5, fraction = 2)
  private BigDecimal price;

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
