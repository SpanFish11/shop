package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Product;
import com.spanfish.shop.entity.SubCategory;
import com.spanfish.shop.entity.request.subcategory.CreateSubCategoryRequest;
import com.spanfish.shop.entity.request.subcategory.UpdateSubCategoryRequest;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.service.ProductService;
import com.spanfish.shop.service.SubcategoryService;
import java.util.List;
import java.util.Objects;
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

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

  private final ProductService productService;
  private final SubcategoryService subcategoryService;

  @GetMapping
  public ResponseEntity<List<SubCategory>> getAll() {

    List<SubCategory> subCategories = subcategoryService.getAll();
    return new ResponseEntity<>(subCategories, HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<SubCategory> getOne(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid subcategory id");
    }
    SubCategory subCategory = subcategoryService.getById(id);
    return new ResponseEntity<>(subCategory, HttpStatus.OK);
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getSubcategoryProducts(
      @PathVariable("id") Long id, Pageable pageable) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid subcategory id");
    }
    Page<Product> products = productService.findAllSubCategoryProducts(id, pageable);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<SubCategory> create(
      @RequestBody CreateSubCategoryRequest createSubCategoryRequest) {

    if (Objects.isNull(createSubCategoryRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    SubCategory subCategory = subcategoryService.create(createSubCategoryRequest);
    return new ResponseEntity<>(subCategory, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<SubCategory> update(
      @RequestBody UpdateSubCategoryRequest updateSubCategoryRequest) {

    if (Objects.isNull(updateSubCategoryRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    SubCategory subCategory = subcategoryService.update(updateSubCategoryRequest);
    return new ResponseEntity<>(subCategory, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<SubCategory> delete(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid subcategory id");
    }
    subcategoryService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
