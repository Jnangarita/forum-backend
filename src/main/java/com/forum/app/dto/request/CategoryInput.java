package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.forum.app.dto.request.groups.CreateGroup;
import com.forum.app.dto.request.groups.UpdateGroup;
import lombok.Data;

@Data
public class CategoryInput {
	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String categoryName;

	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String description;

	@NotNull(groups = {CreateGroup.class})
	private Long createdBy;

	@NotNull(groups = {UpdateGroup.class})
	private Long updatedBy;
}