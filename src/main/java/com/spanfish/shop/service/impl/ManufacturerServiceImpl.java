package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Manufacturer;
import com.spanfish.shop.repository.ManufacturerRepository;
import com.spanfish.shop.service.ManufacturerService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

  private final ManufacturerRepository manufacturerRepository;

  @Override
  public List<Manufacturer> findAll() {
    return manufacturerRepository.findAll();
  }

  @Override
  public Optional<Manufacturer> findById(Long manufacturerId) {
    return manufacturerRepository.findById(manufacturerId);
  }
}
