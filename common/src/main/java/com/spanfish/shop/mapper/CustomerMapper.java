package com.spanfish.shop.mapper;

import com.spanfish.shop.dto.CustomerDTO;
import com.spanfish.shop.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  @Mappings({
    @Mapping(source = "name", target = "customerName"),
    @Mapping(source = "email", target = "customerEmail"),
    @Mapping(source = "password", target = "customerPassword")
  })
  CustomerDTO toCustomerDTO(Customer entity);

  @Mappings({
    @Mapping(source = "customerName", target = "name"),
    @Mapping(source = "customerEmail", target = "email"),
    @Mapping(source = "customerPassword", target = "password")
  })
  Customer fromCustomerDTO(CustomerDTO dto);
}
