package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Manufacturer;
import com.spanfish.shop.entity.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.entity.request.manufacturer.UpdateManufacturerRequest;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.repository.ManufacturerRepository;
import com.spanfish.shop.service.ManufacturerService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(id);
    if (optionalManufacturer.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format("Could not find any manufacturer with the ID %d", id));
    }
    return optionalManufacturer.get();
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

  private Boolean existsById(Long id) {
    return Objects.nonNull(findById(id));
  }
}
