package com.spanfish.shop.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

  private Long id;
  private String name;
  private BigDecimal price;
  private String description;
  private String pictureUrl;
  private String code;
  private String manufacturer;
  private String category;
  private String subCategory;
}
