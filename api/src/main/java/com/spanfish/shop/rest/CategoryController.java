package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Category;
import com.spanfish.shop.entity.Product;
import com.spanfish.shop.entity.request.category.CreateCategoryRequest;
import com.spanfish.shop.entity.request.category.UpdateCategoryRequest;
import com.spanfish.shop.exception.InvalidArgumentException;
import com.spanfish.shop.service.CategoryService;
import com.spanfish.shop.service.ProductService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
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
  public ResponseEntity<Category> getOne(@PathVariable("id") Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid category id");
    }
    Category category = categoryService.getById(id);
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<Product>> getCategoryProducts(
      @PathVariable("id") Long id, Pageable pageable) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid category id");
    }
    Page<Product> products = productService.findAllCategoryProducts(id, pageable);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping()
  public ResponseEntity<Category> addNew(
      @RequestBody @Valid CreateCategoryRequest createCategoryRequest) {

    if (Objects.isNull(createCategoryRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    Category category = categoryService.create(createCategoryRequest);
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<Category> update(@RequestBody UpdateCategoryRequest updateCategoryRequest) {

    if (Objects.isNull(updateCategoryRequest)) {
      throw new InvalidArgumentException("Wrong data");
    }
    Category category = categoryService.update(updateCategoryRequest);
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{id}")
  public ResponseEntity<Category> delete(@PathVariable Long id) {

    if (Objects.isNull(id) || id <= 0) {
      throw new InvalidArgumentException("Invalid category id");
    }
    categoryService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}