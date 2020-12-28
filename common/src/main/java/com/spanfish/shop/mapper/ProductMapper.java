package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(source = "name", target = "name"),
    @Mapping(source = "price", target = "price"),
    @Mapping(source = "description", target = "description"),
    @Mapping(source = "pictureUrl", target = "pictureUrl"),
    @Mapping(source = "code", target = "code"),
    @Mapping(source = "manufacturer.name", target = "manufacturer"),
    @Mapping(source = "category.name", target = "category"),
    @Mapping(source = "subCategory.name", target = "subCategory")
  })
  ProductDTO toDTO(Product product);
}
