package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Manufacturer;
import java.util.List;

public interface ManufacturerService {

  List<Manufacturer> findAll();

  Manufacturer findById(Long id);

  Manufacturer create(Manufacturer manufacturer);

  Manufacturer update(Manufacturer manufacturer);

  void delete(Long manufacturerId);

  Boolean existsById(Long id);
}
