package com.forum.app.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicDTO {
	private Long id;
	private Long idCategory;
	private String question;
	private Long idUser;
	private LocalDateTime creationDate;
	private boolean deleted;

	public TopicDTO(Long id, Long idCategory, String question, Long idUser, LocalDateTime creationDate,
			boolean deleted) {
		this.id = id;
		this.idCategory = idCategory;
		this.question = question;
		this.idUser = idUser;
		this.creationDate = creationDate;
		this.deleted = deleted;
	}
}