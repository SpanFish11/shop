package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Product;
import com.spanfish.shop.entity.SubCategory;
import com.spanfish.shop.service.ProductService;
import com.spanfish.shop.service.SubcategoryService;
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

import static java.util.Objects.isNull;

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
  public ResponseEntity<SubCategory> getOne(@PathVariable("id") Long subCategoryId) {

    if (isNull(subCategoryId)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<SubCategory> optionalSubCategory = subcategoryService.getById(subCategoryId);
    return optionalSubCategory
        .map(subCategory -> new ResponseEntity<>(subCategory, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getSubcategoryProducts(
      @PathVariable("id") Long subCategoryId, @PageableDefault Pageable pageable) {

    Page<Product> products = productService.findAllSubCategoryProducts(subCategoryId, pageable);
    if (products.getTotalElements() == 0) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<SubCategory> create(@RequestBody SubCategory requestSubCategory) {

    if (isNull(requestSubCategory)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    SubCategory subCategory = subcategoryService.create(requestSubCategory);
    return new ResponseEntity<>(subCategory, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<SubCategory> update(@RequestBody SubCategory requestSubCategory) {

    if (isNull(requestSubCategory)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    SubCategory subCategory = subcategoryService.update(requestSubCategory);
    return new ResponseEntity<>(subCategory, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<SubCategory> delete(@PathVariable("id") Long subCategoryId) {

    Optional<SubCategory> optionalSubCategory = subcategoryService.getById(subCategoryId);
    if (optionalSubCategory.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    subcategoryService.delete(subCategoryId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
