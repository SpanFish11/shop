package com.spanfish.shop.repository;

import com.spanfish.shop.model.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {}
