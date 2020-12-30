package com.spanfish.shop.model.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDTO(Long id, BigDecimal totalPrice, List<OrderedProductDTO> cartItems) {
}
