package com.forum.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTopicDTO {
	@NotNull
	Long questionId;

	@NotNull
	Long categoryId;

	@NotBlank
	String question;
}