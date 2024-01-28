package com.forum.app.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.entity.Topic;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.TopicRepository;

@Service
public class TopicService {
	@Autowired
	private TopicRepository topicRepository;

	@Transactional
	public Topic createTopic(SaveTopicDTO payload) {
		try {
			Topic newTopic = new Topic();
			LocalDateTime currentDate = LocalDateTime.now();
			newTopic.setIdCategory(payload.getIdCategory());
			newTopic.setQuestion(payload.getQuestion());
			newTopic.setIdUser(payload.getIdUser());
			newTopic.setCreationDate(currentDate);
			newTopic.setDeleted(false);
			return topicRepository.save(newTopic);
		} catch (Exception e) {
			throw new OwnRuntimeException("Error creating a new question");
		}
	}
}