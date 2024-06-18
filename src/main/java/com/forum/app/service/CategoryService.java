package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.CategoryDTO;
import com.forum.app.dto.CategoryResponseDTO;
import com.forum.app.entity.Category;

public interface CategoryService {

	CategoryResponseDTO createCategory(CategoryDTO payload);

	CategoryResponseDTO getCategoryById(Long id);

	Category findCategoryById(Long id);

	CategoryResponseDTO updateCategory(Long id, CategoryDTO payload);

	List<CategoryResponseDTO> getCategoryList();

	void deleteCategory(Long id);
}