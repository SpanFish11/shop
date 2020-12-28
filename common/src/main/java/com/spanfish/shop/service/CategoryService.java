package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Category;
import com.spanfish.shop.model.request.category.CreateCategoryRequest;
import com.spanfish.shop.model.request.category.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {

  Category getById(Long categoryId);

  List<Category> getAll();

  Category create(CreateCategoryRequest createCategoryRequest);

  Category update(UpdateCategoryRequest updateCategoryRequest);

  void delete(Long categoryId);

  Boolean existsById(Long id);
}
