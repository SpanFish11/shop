package com.spanfish.shop.service.impl;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.model.request.manufacturer.UpdateManufacturerRequest;
import com.spanfish.shop.repository.ManufacturerRepository;
import com.spanfish.shop.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "manufacturer")
public class ManufacturerServiceImpl implements ManufacturerService {

  private final ManufacturerRepository manufacturerRepository;

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size()== 0")
  public List<Manufacturer> findAll() {
    return manufacturerRepository.findAll();
  }

  @Override
  @Cacheable(key = "{#root.methodName, #id}")
  public Manufacturer findById(Long id) {
    return manufacturerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format("Could not find any manufacturer with the ID %d", id)));
  }

  @Override
  public Manufacturer save(CreateManufacturerRequest request) {
    Manufacturer manufacturer = new Manufacturer();
    manufacturer.setName(request.getName());
    manufacturer.setDescription(request.getDescription());
    return manufacturerRepository.save(manufacturer);
  }

  @Override
  public Manufacturer update(UpdateManufacturerRequest request) {
    Manufacturer manufacturer = findById(request.getId());
    manufacturer.setName(request.getName());
    manufacturer.setDescription(request.getDescription());
    return manufacturerRepository.save(manufacturer);
  }

  @Override
  public void delete(Long id) {
    if (existsById(id)) {
      manufacturerRepository.deleteById(id);
    }
  }

  @Override
  public Boolean existsById(Long id) {
    if (!manufacturerRepository.existsById(id)) {
      throw new ResourceNotFoundException(
          String.format("Could not find any manufacturer with the ID %d", id));
    }
    return true;
  }
}
