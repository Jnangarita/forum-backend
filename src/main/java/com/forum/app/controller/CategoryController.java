package com.forum.app.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.CategoryDTO;
import com.forum.app.dto.CategoryResponseDTO;
import com.forum.app.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
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
}