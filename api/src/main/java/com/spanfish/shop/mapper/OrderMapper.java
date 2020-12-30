package com.spanfish.shop.mapper;

import static org.mapstruct.CollectionMappingStrategy.ADDER_PREFERRED;

import com.spanfish.shop.model.dto.OrderDTO;
import com.spanfish.shop.model.dto.OrderedProductDTO;
import com.spanfish.shop.model.entity.Order;
import com.spanfish.shop.model.entity.OrderProduct;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", collectionMappingStrategy = ADDER_PREFERRED)
public interface OrderMapper {

  @Mapping(target = "orderNumber", source = "orderNumber")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "totalPrice", source = "totalPrice")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "orderedProducts", source = "orderProductList")
  OrderDTO toDTO(final Order order);

  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "price", source = "product.price")
  @Mapping(target = "pictureUrl", source = "product.pictureUrl")
  @Mapping(target = "code", source = "product.code")
  @Mapping(target = "amount", source = "amount")
  OrderedProductDTO toOrderedDTO(final OrderProduct source);

  List<OrderDTO> toDTOs(final List<Order> orders);
}
