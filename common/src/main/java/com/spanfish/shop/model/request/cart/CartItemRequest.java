package com.spanfish.shop.model.request.cart;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CartItemRequest {

  @NotNull
  @Min(1)
  private Long productId;

  @NotNull
  @Min(1)
  private Integer amount;
}
