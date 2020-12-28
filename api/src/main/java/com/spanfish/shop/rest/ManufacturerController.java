package com.spanfish.shop.rest;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.model.request.manufacturer.UpdateManufacturerRequest;
import com.spanfish.shop.service.ManufacturerService;
import com.spanfish.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import java.util.List;
import java.util.Objects;

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
  public ResponseEntity<Manufacturer> get(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid manufacturer id");
    }
    return new ResponseEntity<>(manufacturerService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getManufacturersProducts(
      @PathVariable("id") Long id, Pageable pageable) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid manufacturer id");
    }
    return new ResponseEntity<>(
        productService.findAllManufacturersProducts(id, pageable), HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<Manufacturer> addNew(
      @RequestBody CreateManufacturerRequest createManufacturerRequest) {

    if (Objects.isNull(createManufacturerRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    return new ResponseEntity<>(
        manufacturerService.save(createManufacturerRequest), HttpStatus.CREATED);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<Manufacturer> update(
      @RequestBody UpdateManufacturerRequest updateManufacturerRequest) {

    if (Objects.isNull(updateManufacturerRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    return new ResponseEntity<>(
        manufacturerService.update(updateManufacturerRequest), HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<Manufacturer> delete(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid manufacturer id");
    }
    manufacturerService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
