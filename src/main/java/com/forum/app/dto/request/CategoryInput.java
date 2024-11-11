package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CategoryInput {
	@NotBlank
	private String categoryName;

	@NotBlank
	private String description;

	@NotNull
	private Long createdBy;
}