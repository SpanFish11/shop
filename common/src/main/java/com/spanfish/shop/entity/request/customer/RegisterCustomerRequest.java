package com.spanfish.shop.entity.request.customer;

import lombok.Data;

@Data
public class RegisterCustomerRequest {

  private String name;
  private String email;
  private String password;
}
