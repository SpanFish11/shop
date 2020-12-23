package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.SubCategory;
import com.spanfish.shop.entity.request.subcategory.CreateSubCategoryRequest;
import com.spanfish.shop.entity.request.subcategory.UpdateSubCategoryRequest;
import com.spanfish.shop.exception.ResourceNotFoundException;
import com.spanfish.shop.repository.SubcategoryRepository;
import com.spanfish.shop.service.CategoryService;
import com.spanfish.shop.service.SubcategoryService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "subCategory")
public class SubcategoryServiceImpl implements SubcategoryService {

  private final SubcategoryRepository subcategoryRepository;
  private final CategoryService categoryService;

  @Override
  @Cacheable(key = "{#root.methodName, #id}")
  public SubCategory getById(Long id) {

    Optional<SubCategory> optionalSubCategory = subcategoryRepository.findById(id);
    if (optionalSubCategory.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format("Could not find any subcategory with the ID %d", id));
    }
    return optionalSubCategory.get();
  }

  @Override
  @Cacheable(key = "#root.methodName", unless = "#result.size()== 0")
  public List<SubCategory> getAll() {
    return subcategoryRepository.findAll();
  }

  @Override
  public SubCategory create(CreateSubCategoryRequest request) {

    SubCategory subCategory = new SubCategory();
    subCategory.setName(request.getName());
    subCategory.setCategory(categoryService.getById(request.getCategoryId()));
    return subcategoryRepository.save(subCategory);
  }

  @Override
  public SubCategory update(UpdateSubCategoryRequest request) {

    SubCategory subCategory = getById(request.getId());
    subCategory.setName(request.getName());
    subCategory.setCategory(categoryService.getById(request.getCategoryId()));
    return subcategoryRepository.save(subCategory);
  }

  @Override
  public void delete(Long id) {

    if (existsById(id)) {
      subcategoryRepository.deleteById(id);
    }
  }

  private Boolean existsById(Long id) {
    return Objects.nonNull(getById(id));
  }
}
