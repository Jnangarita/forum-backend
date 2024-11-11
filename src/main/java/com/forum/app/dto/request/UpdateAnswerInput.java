package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerInput {
	@NotBlank
	private String answerTxt;
}