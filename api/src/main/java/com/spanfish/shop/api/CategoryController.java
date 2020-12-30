package com.spanfish.shop.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import com.spanfish.shop.mapper.CategoryMapper;
import com.spanfish.shop.mapper.ProductMapper;
import com.spanfish.shop.model.dto.CategoryDTO;
import com.spanfish.shop.model.dto.ProductDTO;
import com.spanfish.shop.model.entity.Category;
import com.spanfish.shop.model.request.category.CreateCategoryRequest;
import com.spanfish.shop.model.request.category.UpdateCategoryRequest;
import com.spanfish.shop.service.CategoryService;
import com.spanfish.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final ProductService productService;
  private final CategoryMapper mapper;
  private final ProductMapper productMapper;

  @Operation(
      summary = "Get all categories",
      description = "Endpoint to get all available categories")
  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAll() {
    final List<Category> categories = categoryService.getAll();
    return ok(mapper.toDTOs(categories));
  }

  @Operation(summary = "Get category", description = "Endpoint to get a category")
  @GetMapping("/{category_id}")
  public ResponseEntity<CategoryDTO> getOne(
      @PathVariable("category_id") @Min(1) @NotNull final Long id) {
    final Category category = categoryService.getById(id);
    return ok(mapper.toDTO(category));
  }

  @Operation(summary = "Get category products", description = "Endpoint for all category products")
  @GetMapping("/{category_id}/products")
  public ResponseEntity<Page<ProductDTO>> getCategoryProducts(
      @PathVariable("category_id") @Min(1) @NotNull final Long id,
      @RequestParam(value = "page", required = false, defaultValue = "0") @Min(0)
          final Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Min(5)
          final Integer pageSize) {
    final Page<ProductDTO> products =
        productService.findAllCategoryProducts(id, page, pageSize).map(productMapper::toDTO);
    return ok(products);
  }

  @Operation(
      summary = "Add category",
      description = "Endpoint to add a new category",
      responses = {@ApiResponse(responseCode = "400", description = "Invalid data")})
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PostMapping()
  public ResponseEntity<CategoryDTO> addNew(
      @RequestBody @Valid final CreateCategoryRequest request) {
    final Category category = categoryService.create(mapper.toCategory(request));
    return new ResponseEntity<>(mapper.toDTO(category), CREATED);
  }

  @Operation(
      summary = "Update category",
      description = "Endpoint to update the information of an existing category",
      responses = {
        @ApiResponse(
            responseCode = "403",
            description = "Access forbidden. You are not allowed to administrate categories.")
      })
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @PutMapping
  public ResponseEntity<CategoryDTO> update(
      @RequestBody @Valid final UpdateCategoryRequest request) {
    final Category category = mapper.toCategory(request, categoryService.getById(request.getId()));
    final Category updated = categoryService.update(category);
    return ok(mapper.toDTO(updated));
  }

  @Operation(summary = "Delete category", description = "Endpoint to delete a category")
  @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
  @DeleteMapping("/{category_id}")
  public ResponseEntity<Category> delete(
      @PathVariable("category_id") @Min(1) @NotNull final Long id) {
    categoryService.delete(id);
    return noContent().build();
  }
}
