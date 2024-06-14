package com.forum.app.service;

import com.forum.app.dto.CategoryDTO;
import com.forum.app.dto.CategoryResponseDTO;

public interface CategoryService {

	CategoryResponseDTO createCategory(CategoryDTO payload);
}