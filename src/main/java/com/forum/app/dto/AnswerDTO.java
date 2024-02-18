package com.forum.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
	@NotNull
    private Long idQuestion;

	@NotBlank
    private String answerTxt;

	@NotNull
    private Long idUser;
}