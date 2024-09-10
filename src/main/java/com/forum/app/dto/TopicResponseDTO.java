package com.forum.app.dto;

import com.forum.app.dto.response.ResponseDTO;
import com.forum.app.entity.Topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponseDTO extends ResponseDTO {
	private String question;
	private char questionStatus;

	public TopicResponseDTO(Topic topic, String message) {
		super(topic.getCreatedAt(), topic.isDeleted(), topic.getId(), message, topic.getUpdatedAt());
		this.question = topic.getQuestion();
		this.questionStatus = topic.getQuestionStatus();
	}
}