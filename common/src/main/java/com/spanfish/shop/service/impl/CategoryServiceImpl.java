package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.Category;
import com.spanfish.shop.entity.request.category.CreateCategoryRequest;
import com.spanfish.shop.entity.request.category.UpdateCategoryRequest;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.repository.CategoryRepository;
import com.spanfish.shop.service.CategoryService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "category")
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size()== 0")
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  @Cacheable(key = "{#root.methodName, #id}")
  public Category getById(Long id) {

    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format("Could not find any category with the ID %d", id));
    }
    return optionalCategory.get();
  }

  @Override
  public Category create(CreateCategoryRequest request) {

    Category category = new Category();
    category.setName(request.getName());
    return categoryRepository.save(category);
  }

  @Override
  public Category update(UpdateCategoryRequest request) {

    Category category = getById(request.getId());
    category.setName(request.getName());
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Long id) {

    if (existsById(id)) {
      categoryRepository.deleteById(id);
    }
  }

  private Boolean existsById(Long id) {
    return Objects.nonNull(getById(id));
  }
}
