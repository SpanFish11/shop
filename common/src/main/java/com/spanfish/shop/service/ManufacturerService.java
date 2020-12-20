package com.spanfish.shop.service;

import com.spanfish.shop.entity.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

  List<Manufacturer> findAll();

  Optional<Manufacturer> findById(Long manufacturerId);

  Manufacturer save(Manufacturer manufacturer);

  Manufacturer update(Manufacturer manufacturer);

  void delete(Long manufacturerId);
}
