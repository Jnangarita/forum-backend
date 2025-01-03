package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.forum.app.mapper.CategoryMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.request.CategoryInput;
import com.forum.app.dto.CategoryOutput;
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
	private final CategoryMapper categoryMapper;

	public CategoryServiceImpl(Utility utility, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.utility = utility;
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Transactional
	@Override
	public CategoryOutput createCategory(CategoryInput payload) {
		try {
			Category newCategory = categoryMapper.convertDtoToEntity(payload);
			Category category = categoryRepository.save(newCategory);
			return new CategoryOutput(category);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.category", null));
		}
	}

	@Override
	public CategoryOutput getCategoryById(Long id) {
		try {
			Category category = findCategoryById(id);
			return new CategoryOutput(category);
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
	public CategoryOutput updateCategory(Long id, CategoryInput payload) {
		try {
			Category categoryToUpdate = findCategoryById(id);
			categoryMapper.updateCategoryFromDto(payload, categoryToUpdate);
			Category category = categoryRepository.save(categoryToUpdate);
			return new CategoryOutput(category);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.category", null));
		}
	}

	@Override
	public List<CategoryOutput> getCategoryList() {
		try {
			List<Map<String, Object>> savedCategoryList = categoryRepository.findCategoryByDeletedFalse();
			List<CategoryOutput> categoryList = new ArrayList<>();
			for (Map<String, Object> category : savedCategoryList) {
				CategoryOutput categoryDto = new CategoryOutput();
				populateCategoryResponseDto(category, categoryDto);
				categoryList.add(categoryDto);
			}
			return categoryList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.category", null));
		}
	}

	private void populateCategoryResponseDto(Map<String, Object> categoryMap, CategoryOutput dto) {
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