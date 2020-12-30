package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.ManufacturerDTO;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.model.request.manufacturer.UpdateManufacturerRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    uses = {ProductMapper.class},
    componentModel = "spring")
public interface ManufacturerMapper {

  @Mapping(target = "name", source = "name")
  Manufacturer toManufacturer(final CreateManufacturerRequest request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  Manufacturer toManufacturer(
      final UpdateManufacturerRequest request, @MappingTarget Manufacturer manufacturer);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  ManufacturerDTO toDTO(final Manufacturer source);

  List<ManufacturerDTO> toDTOs(final List<Manufacturer> manufacturers);
}
