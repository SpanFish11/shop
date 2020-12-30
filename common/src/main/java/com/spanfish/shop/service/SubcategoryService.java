package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.SubCategory;
import java.util.List;

public interface SubcategoryService {

  SubCategory getById(Long id);

  List<SubCategory> getAll();

  SubCategory create(SubCategory subCategory);

  SubCategory update(SubCategory subCategory);

  void delete(Long id);

  Boolean existsById(Long Id);
}
