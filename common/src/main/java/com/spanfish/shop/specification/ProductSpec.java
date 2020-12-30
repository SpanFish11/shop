package com.spanfish.shop.specification;

import com.spanfish.shop.model.entity.Product;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpec {

  private ProductSpec() {}

  public static Specification<Product> maxPrice(final BigDecimal maxPrice) {
    return (root, query, cb) -> {
      if (maxPrice == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.lessThan(root.get("price"), maxPrice);
    };
  }

  public static Specification<Product> minPrice(final BigDecimal minPrice) {
    return (root, query, cb) -> {
      if (minPrice == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    };
  }

  public static Specification<Product> withManufacturer(final Long manufacturer) {
    return (root, query, cb) -> {
      if (manufacturer == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("manufacturer").get("id"), manufacturer);
    };
  }

  public static Specification<Product> withCategory(final Long category) {
    return (root, query, cb) -> {
      if (category == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("category").get("id"), category);
    };
  }

  public static Specification<Product> withSubCategory(final Long subCategory) {
    return (root, query, cb) -> {
      if (subCategory == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("category").get("id"), subCategory);
    };
  }
}
