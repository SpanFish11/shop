package com.spanfish.shop.rest;

import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.entity.SubCategory;
import com.spanfish.shop.model.request.subcategory.CreateSubCategoryRequest;
import com.spanfish.shop.model.request.subcategory.UpdateSubCategoryRequest;
import com.spanfish.shop.service.ProductService;
import com.spanfish.shop.service.SubcategoryService;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

  private final ProductService productService;
  private final SubcategoryService subcategoryService;

  @GetMapping
  public ResponseEntity<List<SubCategory>> getAll() {
    return new ResponseEntity<>(subcategoryService.getAll(), HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<SubCategory> getOne(@PathVariable("id") @Min(1) @NotNull Long id) {
    return new ResponseEntity<>(subcategoryService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getSubcategoryProducts(
      @PathVariable("id") @Min(1) @NotNull Long id, Pageable pageable) {

    return new ResponseEntity<>(
        productService.findAllSubCategoryProducts(id, pageable), HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<SubCategory> create(
      @RequestBody @Valid CreateSubCategoryRequest createSubCategoryRequest) {

    return new ResponseEntity<>(subcategoryService.create(createSubCategoryRequest), HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<SubCategory> update(
      @RequestBody @Valid UpdateSubCategoryRequest updateSubCategoryRequest) {

    return new ResponseEntity<>(subcategoryService.update(updateSubCategoryRequest), HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<SubCategory> delete(@PathVariable("id") @Min(1) @NotNull Long id) {

    subcategoryService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
