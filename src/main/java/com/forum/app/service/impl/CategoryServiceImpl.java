package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.request.CategoryInput;
import com.forum.app.dto.CategoryResponseDTO;
import com.forum.app.dto.request.IdValueInput;
import com.forum.app.entity.Category;
import com.forum.app.enumeration.DbColumns;
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
	public CategoryResponseDTO createCategory(CategoryInput payload) {
		try {
			Category newCategory = new Category();
			populateCategory(newCategory, payload);
			Category category = categoryRepository.save(newCategory);
			return new CategoryResponseDTO(category);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.category", null));
		}
	}

	private void populateCategory(Category category, CategoryInput payload) {
		category.setCategoryName(payload.getCategoryName());
		category.setDescription(payload.getDescription());
		category.setCreatedBy(payload.getCreatedBy());
	}

	@Override
	public CategoryResponseDTO getCategoryById(Long id) {
		try {
			Category category = findCategoryById(id);
			return new CategoryResponseDTO(category);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.category", null));
		}
	}

	@Override
	public Category findCategoryById(Long id) {
		return categoryRepository.getReferenceById(id);
	}

	@Transactional
	@Override
	public CategoryResponseDTO updateCategory(Long id, CategoryInput payload) {
		try {
			// TODO: Actualizar el campo description
			Category categoryToUpdate = findCategoryById(id);
			categoryToUpdate.setCategoryName(payload.getCategoryName());
			categoryToUpdate.setModifiedBy(payload.getCreatedBy());
			Category category = categoryRepository.save(categoryToUpdate);
			return new CategoryResponseDTO(category);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.category", null));
		}
	}

	@Override
	public List<CategoryResponseDTO> getCategoryList() {
		try {
			List<Map<String, Object>> savedCategoryList = categoryRepository.findCategoryByDeletedFalse();
			List<CategoryResponseDTO> categoryList = new ArrayList<>();
			for (Map<String, Object> category : savedCategoryList) {
				CategoryResponseDTO categoryDto = new CategoryResponseDTO();
				populateCategoryResponseDto(category, categoryDto);
				categoryList.add(categoryDto);
			}
			return categoryList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.category", null));
		}
	}

	private void populateCategoryResponseDto(Map<String, Object> categoryMap, CategoryResponseDTO dto) {
		dto.setId(utility.convertToLongType(categoryMap.get(DbColumns.ID.getColumns())));
		dto.setCategoryName((String) categoryMap.get(DbColumns.CATEGORY_NAME.getColumns()));
		dto.setDescription((String) categoryMap.get(DbColumns.DESCRIPTION.getColumns()));
		dto.setNumberQuestion(utility.convertToIntType(categoryMap.get(DbColumns.QUESTION_NUMBER.getColumns())));
		dto.setTime(utility.getDate(categoryMap, DbColumns.DATE.getColumns()));
	}

	@Transactional
	@Override
	public void deleteCategory(Long id) {
		try {
			Category category = findCategoryById(id);
			if (!category.isDeleted()) {
				category.setDeleted(true);
			}
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.deleting.category", null));
		}
	}

	@Override
	public List<IdValueInput> getCategories() {
		try {
			List<Category> categories = categoryRepository.findByDeletedFalse();
			List<IdValueInput> categoryList = new ArrayList<>();
			for (Category category : categories) {
				IdValueInput dto = new IdValueInput();
				dto.setId(category.getId());
				dto.setValue(category.getCategoryName());
				categoryList.add(dto);
			}
			return categoryList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.category", null));
		}
	}
}