package com.spanfish.shop.mapper;

import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.entity.Product;
import com.spanfish.shop.model.request.product.CreateProductRequest;
import com.spanfish.shop.model.request.product.UpdateProductRequest;
import com.spanfish.shop.service.CategoryService;
import com.spanfish.shop.service.ManufacturerService;
import com.spanfish.shop.service.SubcategoryService;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = "spring",
    imports = {
      RandomStringUtils.class,
    })
public abstract class ProductMapper {

  @Autowired protected ManufacturerService manufacturerService;
  @Autowired protected CategoryService categoryService;
  @Autowired protected SubcategoryService subcategoryService;

  @AfterMapping
  protected void setUp(@MappingTarget Product target, final CreateProductRequest source) {
    target.setManufacturer(manufacturerService.findById(source.getManufacturerId()));
    target.setCategory(categoryService.getById(source.getCategoryId()));
    target.setSubCategory(subcategoryService.getById(source.getSubcategoryId()));
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  public abstract Product toProduct(final CreateProductRequest source);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "pictureUrl", source = "picUrl")
  public abstract Product toProduct(
      final UpdateProductRequest source, @MappingTarget Product target);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "pictureUrl", source = "pictureUrl")
  @Mapping(target = "code", source = "code")
  @Mapping(target = "manufacturer", source = "manufacturer.name")
  @Mapping(target = "category", source = "category.name")
  @Mapping(target = "subCategory", source = "subCategory.name")
  public abstract ProductDTO toDTO(final Product source);

  public abstract List<ProductDTO> toDTOs(final List<Product> sources);
}
