package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.CartDTO;
import com.spanfish.shop.model.dto.OrderedProductDTO;
import com.spanfish.shop.model.entity.Cart;
import com.spanfish.shop.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "totalPrice", source = "totalPrice")
  @Mapping(target = "cartItems", source = "cartItemList")
  CartDTO toDTO(final Cart cart);

  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "price", source = "product.price")
  @Mapping(target = "pictureUrl", source = "product.pictureUrl")
  @Mapping(target = "code", source = "product.code")
  @Mapping(target = "amount", source = "amount")
  OrderedProductDTO toOrderedDTO(final CartItem source);
}
