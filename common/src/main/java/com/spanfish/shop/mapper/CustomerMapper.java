package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.CustomerDTO;
import com.spanfish.shop.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(source = "name", target = "name"),
    @Mapping(source = "email", target = "email"),
    @Mapping(source = "registrationDate", target = "registrationDate"),
    @Mapping(source = "contacts.phone", target = "phone"),
    @Mapping(source = "contacts.city", target = "city"),
    @Mapping(source = "contacts.address", target = "address"),
    @Mapping(source = "contacts.zip", target = "zipCode")
  })
  CustomerDTO toDTO(Customer entity);
}
