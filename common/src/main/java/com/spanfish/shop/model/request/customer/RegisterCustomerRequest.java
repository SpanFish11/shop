package com.spanfish.shop.model.request.customer;

import lombok.Data;

@Data
public class RegisterCustomerRequest {

  private String name;
  private String email;
  private String password;
}
