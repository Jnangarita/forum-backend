package com.forum.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerDTO {
	@NotNull
	private Long answerId;

	@NotBlank
	private String answerTxt;
}