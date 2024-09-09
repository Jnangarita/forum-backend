package com.forum.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerDTO {
	@NotBlank
	private String answerTxt;
}