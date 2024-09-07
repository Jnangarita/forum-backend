package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.Topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponseDTO {
	private Long id;
	private Long categoryId;
	private String question;
	private Long userId;
	private char questionStatus;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean deleted;

	public TopicResponseDTO(Topic topic) {
		this.id = topic.getId();
		this.categoryId = topic.getCategoryId();
		this.question = topic.getQuestion();
		this.userId = topic.getUserId();
		this.questionStatus = topic.getQuestionStatus();
		this.creationDate = topic.getCreatedAt();
		this.modificationDate = topic.getUpdatedAt();
		this.deleted = topic.isDeleted();
	}
}