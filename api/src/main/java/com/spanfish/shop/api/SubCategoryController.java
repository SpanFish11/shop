package com.spanfish.shop.api;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.ProductMapper;
import com.spanfish.shop.mapper.SubCategoryMapper;
import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.dto.SubCategoryDTO;
import com.spanfish.shop.model.entity.SubCategory;
import com.spanfish.shop.model.request.subcategory.CreateSubCategoryRequest;
import com.spanfish.shop.model.request.subcategory.UpdateSubCategoryRequest;
import com.spanfish.shop.service.ProductService;
import com.spanfish.shop.service.SubcategoryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

  private final ProductService productService;
  private final SubcategoryService subService;
  private final SubCategoryMapper mapper;
  private final ProductMapper productMapper;

  @Operation(
      summary = "Get all subcategories",
      description = "Endpoint to get all available subcategories")
  @GetMapping
  public ResponseEntity<List<SubCategoryDTO>> getAll() {
    final List<SubCategory> subCategories = subService.getAll();
    return ok(mapper.toDTOs(subCategories));
  }

  @Operation(summary = "Get subcategories", description = "Endpoint to get a subcategories")
  @GetMapping("{subcategory_id}")
  public ResponseEntity<SubCategoryDTO> getOne(
      @PathVariable("subcategory_id") @Min(1) @NotNull final Long id) {
    final SubCategory subCategory = subService.getById(id);
    return ok(mapper.toDTO(subCategory));
  }

  @Operation(
      summary = "Get subcategory products",
      description = "Endpoint for all subcategory products")
  @GetMapping("/{subcategory_id}/products")
  public ResponseEntity<Page<ProductDTO>> getSubcategoryProducts(
      @PathVariable("subcategory_id") @Min(1) @NotNull final Long id,
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize) {
    final Page<ProductDTO> products =
        productService.findAllSubCategoryProducts(id, page, pageSize).map(productMapper::toDTO);
    return ok(products);
  }

  @Operation(summary = "Add subcategory", description = "Endpoint to add a new subcategory")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping
  public ResponseEntity<SubCategoryDTO> create(
      @RequestBody @Valid final CreateSubCategoryRequest request) {
    final SubCategory subCategory = subService.create(mapper.toSubcategory(request));
    return ok(mapper.toDTO(subCategory));
  }

  @Operation(
      summary = "Update subcategory",
      description = "Endpoint to update the information of an existing subcategory")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<SubCategoryDTO> update(
      @RequestBody @Valid final UpdateSubCategoryRequest request) {
    final SubCategory subCategory =
        mapper.toSubCategory(request, subService.getById(request.getId()));
    final SubCategory updated = subService.update(subCategory);
    return ok(mapper.toDTO(updated));
  }

  @Operation(summary = "Delete subcategory", description = "Endpoint to delete a subcategory")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("{subcategory_id}")
  public ResponseEntity<SubCategory> delete(
      @PathVariable("subcategory_id") @Min(1) @NotNull final Long id) {
    subService.delete(id);
    return noContent().build();
  }
}
