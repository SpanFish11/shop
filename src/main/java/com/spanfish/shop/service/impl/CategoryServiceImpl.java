package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Category;
import com.spanfish.shop.repository.CategoryRepository;
import com.spanfish.shop.service.CategoryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Optional<Category> getById(Long id) {
    return categoryRepository.findById(id);
  }

  @Override
  public Category create(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Category update(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }
}
