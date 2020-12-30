package com.spanfish.shop.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.ManufacturerMapper;
import com.spanfish.shop.mapper.ProductMapper;
import com.spanfish.shop.model.dto.ManufacturerDTO;
import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.entity.Manufacturer;
import com.spanfish.shop.model.request.manufacturer.CreateManufacturerRequest;
import com.spanfish.shop.model.request.manufacturer.UpdateManufacturerRequest;
import com.spanfish.shop.service.ManufacturerService;
import com.spanfish.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

  private final ManufacturerService manufacturerService;
  private final ProductService productService;
  private final ManufacturerMapper mapper;
  private final ProductMapper productMapper;

  @Operation(
      summary = "Get all manufacturers",
      description = "Endpoint to get all available manufacturers")
  @GetMapping
  public ResponseEntity<List<ManufacturerDTO>> findAll() {
    final List<Manufacturer> manufacturers = manufacturerService.findAll();
    return ok(mapper.toDTOs(manufacturers));
  }

  @Operation(summary = "Get manufacturer", description = "Endpoint to get a manufacturer")
  @GetMapping("/{manufacturer_id}")
  public ResponseEntity<ManufacturerDTO> get(
      @PathVariable("manufacturer_id") @Min(1) @NotNull final Long id) {
    final Manufacturer manufacturer = manufacturerService.findById(id);
    return ok(mapper.toDTO(manufacturer));
  }

  @Operation(
      summary = "Get manufacturer products",
      description = "Endpoint for all manufacturer products")
  @GetMapping("/{manufacturer_id}/products")
  public ResponseEntity<Page<ProductDTO>> getManufacturersProducts(
      @PathVariable("manufacturer_id") @Min(1) @NotNull final Long id,
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize) {
    final Page<ProductDTO> products =
        productService.findAllManufacturersProducts(id, page, pageSize).map(productMapper::toDTO);
    return ok(products);
  }

  @Operation(summary = "Add manufacturer", description = "Endpoint to add a new manufacturer")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<ManufacturerDTO> addNew(
      @RequestBody @Valid final CreateManufacturerRequest request) {
    final Manufacturer manufacturer = manufacturerService.create(mapper.toManufacturer(request));
    return new ResponseEntity<>(mapper.toDTO(manufacturer), CREATED);
  }

  @Operation(
      summary = "Update manufacturer",
      description = "Endpoint to update the information of an existing manufacturer")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<ManufacturerDTO> update(
      @RequestBody @Valid final UpdateManufacturerRequest request) {
    final Manufacturer manufacturer =
        mapper.toManufacturer(request, manufacturerService.findById(request.getId()));
    final Manufacturer updated = manufacturerService.update(manufacturer);
    return ok(mapper.toDTO(updated));
  }

  @Operation(summary = "Delete manufacturer", description = "Endpoint to delete a manufacturer")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{manufacturer_id}")
  public ResponseEntity<Manufacturer> delete(
      @PathVariable("manufacturer_id") @Min(1) @NotNull final Long id) {
    manufacturerService.delete(id);
    return noContent().build();
  }
}
