package com.spanfish.shop.model.request.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {

  @NotNull
  @Min(1)
  private Long productId;

  @NotNull
  @Min(1)
  private Integer amount;
}
