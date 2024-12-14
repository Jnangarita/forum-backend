package com.forum.app.dto;

import com.forum.app.dto.response.ResponseDTO;
import com.forum.app.entity.Topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicOutput extends ResponseDTO {
	private String question;
	private char questionStatus;

	public TopicOutput(Topic topic) {
		super(topic.getCreatedAt(), topic.isDeleted(), topic.getId(), topic.getUpdatedAt());
		this.question = topic.getQuestion();
		this.questionStatus = topic.getQuestionStatus();
	}
}