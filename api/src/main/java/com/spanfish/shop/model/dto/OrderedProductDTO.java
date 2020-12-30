package com.spanfish.shop.model.dto;

import java.math.BigDecimal;

public record OrderedProductDTO(Long id, String name, BigDecimal price, String pictureUrl, String code,
                                Integer amount) {
}
