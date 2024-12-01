package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.forum.app.dto.request.groups.CreateGroup;
import com.forum.app.dto.request.groups.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicInput {
	@NotNull(groups = {CreateGroup.class, UpdateGroup.class})
	private Long categoryId;

	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String question;

	@NotBlank(groups = {CreateGroup.class})
	private String titleQuestion;

	@NotNull(groups = {CreateGroup.class})
	private Long userId;
}