package com.spanfish.shop.service.impl;

import static java.lang.String.format;

import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.model.entity.SubCategory;
import com.spanfish.shop.repository.SubcategoryRepository;
import com.spanfish.shop.service.SubcategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "subCategory")
public class SubcategoryServiceImpl implements SubcategoryService {

  private static final String EXCEPTION_MESSAGE = "Could not find any subcategory with the ID %d";

  private final SubcategoryRepository subcategoryRepository;

  @Override
  @Cacheable(key = "#id")
  public SubCategory getById(final Long id) {
    return subcategoryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id)));
  }

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size()== 0")
  public List<SubCategory> getAll() {
    return subcategoryRepository.findAll();
  }

  @Override
  public SubCategory create(final SubCategory subCategory) {
    return subcategoryRepository.save(subCategory);
  }

  @Override
  @CachePut(key = "#subCategory.id")
  public SubCategory update(final SubCategory subCategory) {
    return subcategoryRepository.save(subCategory);
  }

  @Override
  @CacheEvict(key = "#id", allEntries = true)
  public void delete(final Long id) {
    if (existsById(id)) {
      subcategoryRepository.deleteById(id);
    }
  }

  @Override
  public Boolean existsById(final Long id) {
    if (!subcategoryRepository.existsById(id)) {
      throw new ResourceNotFoundException(format(EXCEPTION_MESSAGE, id));
    }
    return true;
  }
}
