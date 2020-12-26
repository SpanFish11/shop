package com.spanfish.shop.entity.request.customer;

import lombok.Data;

@Data
public class UpdateCustomerAddressRequest {

  private String city;
  private Integer zip;
  private String address;
}
