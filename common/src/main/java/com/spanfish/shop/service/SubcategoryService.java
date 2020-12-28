package com.spanfish.shop.service;

import com.spanfish.shop.model.entity.SubCategory;
import com.spanfish.shop.model.request.subcategory.CreateSubCategoryRequest;
import com.spanfish.shop.model.request.subcategory.UpdateSubCategoryRequest;

import java.util.List;

public interface SubcategoryService {

  SubCategory getById(Long id);

  List<SubCategory> getAll();

  SubCategory create(CreateSubCategoryRequest createSubCategoryRequest);

  SubCategory update(UpdateSubCategoryRequest updateSubCategoryRequest);

  void delete(Long id);

  Boolean existsById(Long Id);
}
