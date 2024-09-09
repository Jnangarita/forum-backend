package com.forum.app.dto.response;

import com.forum.app.entity.Answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDTO extends ResponseDTO {
	private char answerStatus;
	private String answerTxt;

	public AnswerResponseDTO(Answer answer, String message) {
		super(answer.getCreatedAt(), answer.isDeleted(), answer.getId(), message, answer.getUpdatedAt());
		this.answerTxt = answer.getAnswerTxt();
		this.answerStatus = answer.getAnswerStatus();
	}
}