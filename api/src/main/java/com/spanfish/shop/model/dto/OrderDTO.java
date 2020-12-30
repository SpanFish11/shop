package com.spanfish.shop.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record OrderDTO(String orderNumber, Date date, BigDecimal totalPrice, String status,
                       List<OrderedProductDTO> orderedProducts) {
}
