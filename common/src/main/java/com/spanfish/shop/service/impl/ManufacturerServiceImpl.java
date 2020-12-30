package com.spanfish.shop.service.impl;

import static java.lang.String.format;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.repository.ManufacturerRepository;
import com.spanfish.shop.service.ManufacturerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "manufacturer")
public class ManufacturerServiceImpl implements ManufacturerService {

  private static final String EXCEPTION_MESSAGE = "Could not find any manufacturer with the ID %d";

  private final ManufacturerRepository manufacturerRepository;

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size()== 0")
  public List<Manufacturer> findAll() {
    return manufacturerRepository.findAll();
  }

  @Override
  @Cacheable(key = "#id")
  public Manufacturer findById(final Long id) {
    return manufacturerRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id)));
  }

  @Override
  public Manufacturer create(final Manufacturer manufacturer) {
    return manufacturerRepository.save(manufacturer);
  }

  @Override
  @CachePut(key = "#manufacturer.id")
  public Manufacturer update(final Manufacturer manufacturer) {
    return manufacturerRepository.save(manufacturer);
  }

  @Override
  @CacheEvict(key = "#id", allEntries = true)
  public void delete(final Long id) {
    if (existsById(id)) {
      manufacturerRepository.deleteById(id);
    }
  }

  @Override
  public Boolean existsById(final Long id) {
    if (!manufacturerRepository.existsById(id)) {
      throw new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id));
    }
    return true;
  }
}
