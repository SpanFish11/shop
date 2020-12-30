package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.SubCategoryDTO;
import com.spanfish.shop.model.entity.SubCategory;
import com.spanfish.shop.model.request.subcategory.CreateSubCategoryRequest;
import com.spanfish.shop.model.request.subcategory.UpdateSubCategoryRequest;
import com.spanfish.shop.service.CategoryService;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    uses = {ProductMapper.class},
    componentModel = "spring")
public abstract class SubCategoryMapper {

  @Autowired protected CategoryService categoryService;

  @AfterMapping
  protected void setUp(@MappingTarget SubCategory target, final CreateSubCategoryRequest source) {
    target.setCategory(categoryService.getById(source.getCategoryId()));
  }

  public abstract SubCategory toSubcategory(final CreateSubCategoryRequest source);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  public abstract SubCategory toSubCategory(
      final UpdateSubCategoryRequest source, @MappingTarget SubCategory target);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  public abstract SubCategoryDTO toDTO(final SubCategory source);

  public abstract List<SubCategoryDTO> toDTOs(final List<SubCategory> targets);
}
