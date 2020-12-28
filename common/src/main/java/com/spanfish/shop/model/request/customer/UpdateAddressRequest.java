package com.spanfish.shop.model.request.customer;

import lombok.Data;

@Data
public class UpdateAddressRequest {

  private String city;
  private Integer zip;
  private String address;
}
