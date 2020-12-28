package com.spanfish.shop.rest;

import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.mapper.ProductMapper;
import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.request.product.CreateProductRequest;
import com.spanfish.shop.model.request.product.UpdateProductRequest;
import com.spanfish.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductMapper productMapper;

  @GetMapping
  public ResponseEntity<Page<Product>> findAll(@PageableDefault Pageable pageable) {

    Page<Product> products = productService.findAll(pageable);
    if (products.getTotalElements() == 0) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> get(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid product id");
    }
    return new ResponseEntity<>(productMapper.toDTO(productService.findById(id)), HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<Product> create(@RequestBody CreateProductRequest createProductRequest) {

    if (createProductRequest == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(productService.create(createProductRequest), HttpStatus.CREATED);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<Product> update(@RequestBody UpdateProductRequest updateProductRequest) {

    if (updateProductRequest == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(productService.update(updateProductRequest), HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping("/add")
  public ResponseEntity<ProductDTO> addProduct(
      @RequestPart(value = "product") String product,
      @RequestPart(value = "photo", required = false) MultipartFile photo) {

    if (StringUtils.isBlank(product)) {
      throw new InvalidArgumentException("Cannot be empty");
    }
    return new ResponseEntity<>(
        productMapper.toDTO(productService.addProduct(product, photo)), HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping("/update")
  public ResponseEntity<ProductDTO> updateProduct(
      @RequestPart(value = "product") String product,
      @RequestPart(value = "photo", required = false) MultipartFile photo) {

    if (StringUtils.isBlank(product)) {
      throw new InvalidArgumentException("Cannot be empty");
    }
    return new ResponseEntity<>(
        productMapper.toDTO(productService.updateProduct(product, photo)), HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<Product> delete(@PathVariable Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid product id");
    }
    productService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
