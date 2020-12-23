package com.spanfish.shop.service;

import com.spanfish.shop.entity.Manufacturer;
import com.spanfish.shop.entity.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.entity.request.manufacturer.UpdateManufacturerRequest;
import java.util.List;

public interface ManufacturerService {

  List<Manufacturer> findAll();

  Manufacturer findById(Long id);

  Manufacturer save(CreateManufacturerRequest createManufacturerRequest);

  Manufacturer update(UpdateManufacturerRequest updateManufacturerRequest);

  void delete(Long manufacturerId);
}
