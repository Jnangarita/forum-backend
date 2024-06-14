package com.forum.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CategoryDTO {
	@NotBlank
	private String categoryName;

	@NotNull
	private Long createdBy;
}