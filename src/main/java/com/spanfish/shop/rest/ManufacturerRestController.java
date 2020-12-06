package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Manufacturer;
import com.spanfish.shop.service.ManufacturerService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerRestController {

  private final ManufacturerService manufacturerService;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Manufacturer>> findAll() {
    List<Manufacturer> manufacturers = manufacturerService.findAll();
    if (manufacturers.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Manufacturer> get(@PathVariable("id") Long manufacturerId) {
    if (manufacturerId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Manufacturer> manufacturerOptional = manufacturerService.findById(manufacturerId);
    return manufacturerOptional
        .map(manufacturer -> new ResponseEntity<>(manufacturer, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
