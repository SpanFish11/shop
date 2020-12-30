package com.spanfish.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  PROCESSING(0),
  CONFIRMED(1),
  COMPLETED(2),
  CANCELLED(3),
  UNKNOWN(4);

  private final Integer id;

  public static OrderStatus findById(final int searchId) {
    for (OrderStatus o : OrderStatus.values()) {
      if (o.getId() == searchId) {
        return o;
      }
    }
    return UNKNOWN;
  }
}
