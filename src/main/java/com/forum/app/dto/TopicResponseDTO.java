package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.Topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponseDTO {
	private Long id;
	private Long idCategory;
	private String question;
	private Long idUser;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean deleted;

	public TopicResponseDTO(Topic topic) {
		this.id = topic.getId();
		this.idCategory = topic.getIdCategory();
		this.question = topic.getQuestion();
		this.idUser = topic.getIdUser();
		this.creationDate = topic.getCreationDate();
		this.modificationDate = topic.getModificationDate();
		this.deleted = topic.isDeleted();
	}
}