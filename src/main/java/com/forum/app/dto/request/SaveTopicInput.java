package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveTopicInput {
	@NotNull
	private Long categoryId;

	@NotBlank
	private String titleQuestion;

	@NotBlank
	private String question;

	@NotNull
	private Long userId;
}