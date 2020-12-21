package com.spanfish.shop.service;

import com.spanfish.shop.entity.SubCategory;
import java.util.List;
import java.util.Optional;

public interface SubcategoryService {

  Optional<SubCategory> getById(Long id);

  List<SubCategory> getAll();

  SubCategory create(SubCategory subCategory);

  SubCategory update(SubCategory subCategory);

  void delete(Long id);
}
