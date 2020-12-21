package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Category;
import com.spanfish.shop.entity.Product;
import com.spanfish.shop.service.CategoryService;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<Category>> getAll() {

    List<Category> categories = categoryService.getAll();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getOne(@PathVariable("id") Long categoryId) {

    if (categoryId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Category> categoryOptional = categoryService.getById(categoryId);
    return categoryOptional
        .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getCategoryProducts(
      @PathVariable("id") Long categoryId, @PageableDefault Pageable pageable) {

    Page<Product> products = productService.findAllCategoryProducts(categoryId, pageable);
    if (products.getTotalElements() == 0) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping()
  public ResponseEntity<Category> addNew(@RequestBody Category requestCategory) {

    if (requestCategory == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Category category = categoryService.create(requestCategory);
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<Category> update(@RequestBody Category requestCategory) {

    if (requestCategory == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Category category = categoryService.update(requestCategory);
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @Secured({"ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<Category> delete(@PathVariable Long id) {

    Optional<Category> category = categoryService.getById(id);
    if (category.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    categoryService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
