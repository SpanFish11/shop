package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Category;
import com.spanfish.shop.service.CategoryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryRestController {

  private final CategoryService categoryService;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Category>> getAll() {

    List<Category> categories = categoryService.getAll();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> getOne(@PathVariable("id") Long categoryId) {
    if (categoryId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Category> categoryOptional = categoryService.getById(categoryId);
    return categoryOptional
        .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  //  @Secured(Roles.Code.ADMIN) ROLE_ADMIN
  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> addNew(@RequestBody Category requestCategory) {

    if (requestCategory == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Category category = categoryService.create(requestCategory);
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  //  @Secured(Roles.Code.ADMIN) ROLE_ADMIN
  @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> update(@RequestBody Category requestCategory) {

    if (requestCategory == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Category category = categoryService.update(requestCategory);
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  //  @Secured(Roles.Code.ADMIN) ROLE_ADMIN
  @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> delete(@PathVariable Long id) {
    Optional<Category> category = categoryService.getById(id);

    if (category.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    categoryService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
