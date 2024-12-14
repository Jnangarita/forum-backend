package com.forum.app.dto.response;

import com.forum.app.entity.Answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOutput extends ResponseDTO {
	private char answerStatus;
	private String answerTxt;

	public AnswerOutput(Answer answer) {
		super(answer.getCreatedAt(), answer.isDeleted(), answer.getId(), answer.getUpdatedAt());
		this.answerTxt = answer.getAnswerTxt();
		this.answerStatus = answer.getAnswerStatus();
	}
}