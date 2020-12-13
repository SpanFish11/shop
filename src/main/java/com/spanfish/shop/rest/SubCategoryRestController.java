package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Product;
import com.spanfish.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubCategoryRestController {

  private final ProductService productService;

  @GetMapping(value = "/{id}/products", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Product>> getSubcategoryProducts(
      @PathVariable("id") Long subCategoryId, @PageableDefault Pageable pageable) {

    Page<Product> products = productService.findAllSubCategoryProducts(subCategoryId, pageable);
    if (products.getTotalElements() == 0) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(products, HttpStatus.OK);
  }
}
