package com.spanfish.shop.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDTO {

  private Long id;
  private String name;
  private String email;
  private Date registrationDate;
  private String phone;
  private String city;
  private String address;
  private Integer zipCode;
}
