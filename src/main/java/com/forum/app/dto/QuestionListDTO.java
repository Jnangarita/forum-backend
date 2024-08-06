package com.forum.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionListDTO {
	private Integer totalQuestions;
	private List<QuestionResponseDTO> questionList;
}