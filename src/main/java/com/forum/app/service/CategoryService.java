package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.request.CategoryInput;
import com.forum.app.dto.CategoryOutput;
import com.forum.app.dto.request.IdValueInput;
import com.forum.app.entity.Category;

public interface CategoryService {

	CategoryOutput createCategory(CategoryInput payload);

	CategoryOutput getCategoryById(Long id);

	Category findCategoryById(Long id);

	CategoryOutput updateCategory(Long id, CategoryInput payload);

	List<CategoryOutput> getCategoryList();

	void deleteCategory(Long id);

	List<IdValueInput> getCategories();
}