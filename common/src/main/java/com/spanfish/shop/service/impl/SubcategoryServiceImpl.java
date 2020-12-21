package com.spanfish.shop.service.impl;

import com.spanfish.shop.entity.SubCategory;
import com.spanfish.shop.repository.SubcategoryRepository;
import com.spanfish.shop.service.SubcategoryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

  private final SubcategoryRepository subcategoryRepository;

  @Override
  public Optional<SubCategory> getById(Long id) {
    return subcategoryRepository.findById(id);
  }

  @Override
  public List<SubCategory> getAll() {
    return subcategoryRepository.findAll();
  }

  @Override
  public SubCategory create(SubCategory subCategory) {
    return subcategoryRepository.save(subCategory);
  }

  @Override
  public SubCategory update(SubCategory subCategory) {
    return subcategoryRepository.save(subCategory);
  }

  @Override
  public void delete(Long id) {
    subcategoryRepository.deleteById(id);
  }
}
