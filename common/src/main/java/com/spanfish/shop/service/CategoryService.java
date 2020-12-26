package com.spanfish.shop.service;

import com.spanfish.shop.entity.Category;
import com.spanfish.shop.entity.request.category.CreateCategoryRequest;
import com.spanfish.shop.entity.request.category.UpdateCategoryRequest;
import java.util.List;

public interface CategoryService {

  Category getById(Long categoryId);

  List<Category> getAll();

  Category create(CreateCategoryRequest createCategoryRequest);

  Category update(UpdateCategoryRequest updateCategoryRequest);

  void delete(Long categoryId);

  Boolean existsById(Long id);
}
