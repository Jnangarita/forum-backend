package com.forum.app.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.CategoryDTO;
import com.forum.app.dto.CategoryResponseDTO;
import com.forum.app.dto.IdValueDTO;
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
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.category", null));
		}
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
	public CategoryResponseDTO updateCategory(Long id, CategoryDTO payload) {
		try {
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
			for (Map<String, Object> userMap : savedCategoryList) {
				Long id = ((Number) userMap.get("id")).longValue();
				String categoryName = (String) userMap.get("nombre_categoria");
				String description = (String) userMap.get("descripcion");
				Integer numberQuestion = ((Number) userMap.get("numero_pregunta")).intValue();
				LocalDateTime time = ((Timestamp) userMap.get("fecha")).toLocalDateTime();

				CategoryResponseDTO userDto = new CategoryResponseDTO(categoryName, description, id, numberQuestion,
						time);
				categoryList.add(userDto);
			}
			return categoryList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.category", null));
		}
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
	public List<IdValueDTO> getCategories() {
		try {
			List<Category> categories = categoryRepository.findByDeletedFalse();
			List<IdValueDTO> categoryList = new ArrayList<>();
			for (Category category : categories) {
				IdValueDTO dto = new IdValueDTO();
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