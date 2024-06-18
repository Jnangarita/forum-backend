package com.forum.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.CategoryDTO;
import com.forum.app.dto.CategoryResponseDTO;
import com.forum.app.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/categories")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

	private final String basePath;
	private final CategoryService categoryService;

	public CategoryController(@Value("${spring.data.rest.basePath}") String basePath, CategoryService categoryService) {
		this.basePath = basePath;
		this.categoryService = categoryService;
	}

	@Operation(summary = "Save a category")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		CategoryResponseDTO category = categoryService.createCategory(payload);
		URI url = uriComponentsBuilder.path(basePath + "/v1/categories/{id}").buildAndExpand(category.getId()).toUri();
		return ResponseEntity.created(url).body(category);
	}

	@Operation(summary = "Gets a category by id")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CategoryResponseDTO> getCategory(
			@Parameter(description = "Id of the category to search") @PathVariable Long id) {
		CategoryResponseDTO category = categoryService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}

	@Operation(summary = "Update a category")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CategoryResponseDTO> updateCategory(
			@Parameter(description = "Id of the category to be updated") @PathVariable Long id,
			@RequestBody @Valid CategoryDTO payload) {
		CategoryResponseDTO category = categoryService.updateCategory(id, payload);
		return ResponseEntity.ok(category);
	}

	@Operation(summary = "Get category list")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<CategoryResponseDTO>> getCategoryList() {
		List<CategoryResponseDTO> categoryList = categoryService.getCategoryList();
		return ResponseEntity.ok(categoryList);
	}

	@Operation(summary = "Delete a category")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<Void> deleteCategory(
			@Parameter(description = "Id of the category to search") @PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}