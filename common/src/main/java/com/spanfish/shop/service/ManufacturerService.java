package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.model.request.manufacturer.UpdateManufacturerRequest;

import java.util.List;

public interface ManufacturerService {

  List<Manufacturer> findAll();

  Manufacturer findById(Long id);

  Manufacturer save(CreateManufacturerRequest createManufacturerRequest);

  Manufacturer update(UpdateManufacturerRequest updateManufacturerRequest);

  void delete(Long manufacturerId);

  Boolean existsById(Long id);
}
