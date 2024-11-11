package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTopicInput {
	@NotNull
	Long categoryId;

	@NotBlank
	String question;
}