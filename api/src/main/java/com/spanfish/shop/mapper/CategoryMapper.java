package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.CategoryDTO;
import com.spanfish.shop.model.entity.Category;
import com.spanfish.shop.model.request.category.CreateCategoryRequest;
import com.spanfish.shop.model.request.category.UpdateCategoryRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  @Mapping(target = "name", source = "name")
  Category toCategory(final CreateCategoryRequest request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  Category toCategory(final UpdateCategoryRequest request, @MappingTarget Category category);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  CategoryDTO toDTO(final Category category);

  List<CategoryDTO> toDTOs(final List<Category> categories);
}
