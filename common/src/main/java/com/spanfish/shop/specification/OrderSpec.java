package com.spanfish.shop.specification;

import com.spanfish.shop.model.entity.Order;
import org.springframework.data.jpa.domain.Specification;

public final class OrderSpec {

  private OrderSpec() {}

  public static Specification<Order> witchNumber(final String number) {
    return (root, query, cb) -> {
      if (number == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("orderNumber"), number);
    };
  }
}
