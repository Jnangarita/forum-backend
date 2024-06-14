package com.forum.app.service.impl;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.CategoryDTO;
import com.forum.app.dto.CategoryResponseDTO;
import com.forum.app.entity.Category;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.CategoryRepository;
import com.forum.app.service.CategoryService;
import com.forum.app.utils.Utility;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final Utility utility;
	private final CategoryRepository categoryRepository;

	public CategoryServiceImpl(Utility utility, CategoryRepository categoryRepository) {
		this.utility = utility;
		this.categoryRepository = categoryRepository;
	}

	@Transactional
	@Override
	public CategoryResponseDTO createCategory(CategoryDTO payload) {
		try {
			Category newCategory = new Category();
			newCategory.setCategoryName(payload.getCategoryName());
			newCategory.setCreatedBy(payload.getCreatedBy());
			newCategory.setDeleted(false);
			Category category = categoryRepository.save(newCategory);
			return new CategoryResponseDTO(category);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.answer", null));
		}
	}
}