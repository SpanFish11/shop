package com.spanfish.shop.model.request.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

  private String name;
  private BigDecimal price;
  private String description;
  private Long manufacturerId;
  private Long categoryId;
  private Long subcategoryId;
}
