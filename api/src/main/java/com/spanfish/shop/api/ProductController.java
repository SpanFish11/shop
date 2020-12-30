package com.spanfish.shop.api;

import static java.util.List.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.ProductMapper;
import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.request.product.CreateProductRequest;
import com.spanfish.shop.model.request.product.UpdateProductRequest;
import com.spanfish.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductMapper mapper;

  @Operation(
      summary = "Get all products",
      description =
          "Endpoint to get all available products with the ability to sort by different criteria")
  @GetMapping
  public ResponseEntity<Page<ProductDTO>> findAll(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize,
      @RequestParam(value = "sort", required = false) final String sort,
      @RequestParam(value = "manufacturer", required = false) @Min(1) final Long manufacturerId,
      @RequestParam(value = "category", required = false) @Min(1) final Long categoryId,
      @RequestParam(value = "subcategory", required = false) @Min(1) final Long subcategoryId,
      @RequestParam(value = "minPrice", required = false, defaultValue = "0") @Min(0)
          final Double minPrice,
      @RequestParam(value = "maxPrice", required = false, defaultValue = "1000") @Min(1)
          final Double maxPrice) {
    final Page<ProductDTO> products =
        productService
            .findAll(
                page, pageSize, sort, manufacturerId, categoryId, subcategoryId, minPrice, maxPrice)
            .map(mapper::toDTO);
    return ok(products);
  }

  @Operation(summary = "Search", description = "Endpoint for searching products by search bar")
  @GetMapping("/search")
  public ResponseEntity<Page<ProductDTO>> search(
      @RequestParam(value = "search", required = false) final String query,
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize) {
    final Page<ProductDTO> products =
        productService.search(query, page, pageSize).map(mapper::toDTO);
    return ok(products);
  }

  @Operation(summary = "Get product", description = "Endpoint for detailed product information")
  @GetMapping("/{product_id}")
  public ResponseEntity<ProductDTO> get(
      @PathVariable("product_id") @Min(1) @NotNull final Long id) {
    final Product product = productService.findById(id);
    return ok(mapper.toDTO(product));
  }

  @Operation(summary = "Add product", description = "Endpoint to add a new product")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<ProductDTO> create(@RequestBody @Valid final CreateProductRequest request) {
    final Product product = productService.create(mapper.toProduct(request));
    return new ResponseEntity<>(mapper.toDTO(product), CREATED);
  }

  @Operation(
      summary = "Update product",
      description = "Endpoint to update the information of an existing product")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<ProductDTO> update(@RequestBody @Valid final UpdateProductRequest request) {
    final Product product = mapper.toProduct(request, productService.findById(request.getId()));
    final Product updated = productService.update(product);
    return ok(mapper.toDTO(updated));
  }

  @Operation(summary = "Change picture", description = "Endpoint to change product photo")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping("/{product_id}/update_photo")
  public ResponseEntity<ProductDTO> updateProductPhoto(
      @PathVariable("product_id") Long id, @RequestPart(value = "photo") final MultipartFile photo)
      throws IOException, HttpMediaTypeNotSupportedException {
    checkImageMediaType(photo.getContentType());
    final Product product = productService.updateProductPhoto(id, photo);
    return ok(mapper.toDTO(product));
  }

  @Operation(summary = "Delete product", description = "Endpoint to delete a product")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{product_id}")
  public ResponseEntity<Product> delete(
      @PathVariable("product_id") @Min(1) @NotNull final Long id) {
    productService.delete(id);
    return noContent().build();
  }

  private void checkImageMediaType(final String type) throws HttpMediaTypeNotSupportedException {
    if (!Objects.equals(type, IMAGE_JPEG_VALUE) && !Objects.equals(type, IMAGE_PNG_VALUE)) {
      throw new HttpMediaTypeNotSupportedException(parseMediaType(type), of(IMAGE_JPEG, IMAGE_PNG));
    }
  }
}
