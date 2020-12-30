package com.spanfish.shop.service.impl;

import static java.lang.String.format;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.Category;
import com.spanfish.shop.repository.CategoryRepository;
import com.spanfish.shop.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "category")
public class CategoryServiceImpl implements CategoryService {

  private static final String EXCEPTION_MESSAGE = "Could not find any category with the ID %d";

  private final CategoryRepository categoryRepository;

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size() == 0")
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  @Cacheable(key = "#id")
  public Category getById(final Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id)));
  }

  @Override
  public Category create(final Category category) {
    return categoryRepository.save(category);
  }

  @Override
  @CachePut(key = "#category.id")
  public Category update(final Category category) {
    return categoryRepository.save(category);
  }

  @Override
  @CacheEvict(key = "#id", allEntries = true)
  public void delete(final Long id) {
    if (existsById(id)) {
      categoryRepository.deleteById(id);
    }
  }

  @Override
  public Boolean existsById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id));
    }
    return true;
  }
}
