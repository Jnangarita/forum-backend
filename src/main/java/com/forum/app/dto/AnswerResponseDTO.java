package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.Answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDTO {
	private Long id;
	private Long idQuestion;
	private String answerTxt;
	private Long idUser;
	private char answerStatus;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean deleted;

	public AnswerResponseDTO(Answer answer) {
		this.id = answer.getId();
		this.idQuestion = answer.getIdQuestion();
		this.answerTxt = answer.getAnswerTxt();
		this.idUser = answer.getIdUser();
		this.answerStatus = answer.getAnswerStatus();
		this.creationDate = answer.getCreationDate();
		this.modificationDate = answer.getModificationDate();
		this.deleted = answer.isDeleted();
	}
}