package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Manufacturer;
import com.spanfish.shop.entity.Product;
import com.spanfish.shop.service.ManufacturerService;
import com.spanfish.shop.service.ProductService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

  private final ManufacturerService manufacturerService;
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<Manufacturer>> findAll() {

    List<Manufacturer> manufacturers = manufacturerService.findAll();
    if (manufacturers.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Manufacturer> get(@PathVariable("id") Long manufacturerId) {

    if (manufacturerId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Manufacturer> manufacturerOptional = manufacturerService.findById(manufacturerId);
    return manufacturerOptional
        .map(manufacturer -> new ResponseEntity<>(manufacturer, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // TODO или по названию производителя
  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getManufacturersProducts(
      @PathVariable("id") Long manufacturerId, @PageableDefault Pageable pageable) {

    Page<Product> products = productService.findAllManufacturersProducts(manufacturerId, pageable);
    if (products.getTotalElements() == 0) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<Manufacturer> addNew(@RequestBody Manufacturer requestManufacturer) {

    if (requestManufacturer == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Manufacturer manufacturer = manufacturerService.save(requestManufacturer);
    return new ResponseEntity<>(manufacturer, HttpStatus.CREATED);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<Manufacturer> update(@RequestBody Manufacturer requestManufacturer) {

    if (requestManufacturer == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Manufacturer manufacturer = manufacturerService.update(requestManufacturer);
    return new ResponseEntity<>(manufacturer, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<Manufacturer> delete(@PathVariable Long id) {

    Optional<Manufacturer> manufacturerOptional = manufacturerService.findById(id);
    if (manufacturerOptional.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    manufacturerService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
