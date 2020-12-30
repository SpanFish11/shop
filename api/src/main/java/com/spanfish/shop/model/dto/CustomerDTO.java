package com.spanfish.shop.model.dto;

import java.util.Date;

public record CustomerDTO(Long id, String name, String email, Date registrationDate, String phone, String city,
                          String address, Integer zipCode) {
}
