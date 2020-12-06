package com.spanfish.shop.service;

import com.spanfish.shop.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

	Optional<Category> getById(Long id);

	List<Category> getAll();

	Category create(Category category);

	Category update(Category category);

	void delete(Long id);
}
