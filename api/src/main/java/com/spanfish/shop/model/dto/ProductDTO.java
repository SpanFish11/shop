package com.spanfish.shop.model.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id, String name, BigDecimal price, String pictureUrl, String code, String manufacturer,
                         String category, String subCategory) {
}
