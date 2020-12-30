package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.Category;
import java.util.List;

public interface CategoryService {

  Category getById(Long categoryId);

  List<Category> getAll();

  Category create(Category category);

  Category update(Category category);

  void delete(Long categoryId);

  Boolean existsById(Long id);
}
