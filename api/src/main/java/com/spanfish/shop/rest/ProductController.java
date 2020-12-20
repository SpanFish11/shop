package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Product;
import com.spanfish.shop.service.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Product>> findAll(@PageableDefault Pageable pageable) {

    Page<Product> products = productService.findAll(pageable);
    if (products.getTotalElements() == 0) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> get(@PathVariable("id") Long productId) {
    if (productId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Product> productOptional = productService.findById(productId);
    return productOptional
        .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> create(@RequestBody Product requestProduct) {

    if (requestProduct == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Product product = productService.create(requestProduct);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> update(@RequestBody Product requestProduct) {

    if (requestProduct == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Product product = productService.update(requestProduct);
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> delete(@PathVariable Long id) {
    Optional<Product> product = productService.findById(id);

    if (product.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    productService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
