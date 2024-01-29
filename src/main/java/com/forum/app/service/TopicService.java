package com.forum.app.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicDTO;
import com.forum.app.dto.UpdateTopicDTO;
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
			throw new OwnRuntimeException("Error Creating New Question");
		}
	}

	public TopicDTO getTopic(Long id) {
		Topic topic = topicRepository.getReferenceById(id);
		Long idCategory = topic.getIdCategory();
		String question = topic.getQuestion();
		Long idUser = topic.getIdUser();
		LocalDateTime creationDate = topic.getCreationDate();
		boolean deleted = topic.isDeleted();
		return new TopicDTO(id, idCategory, question, idUser, creationDate, deleted);
	}

	@Transactional
	public TopicDTO updateTopic(UpdateTopicDTO payload) {
		try {
			Topic topic = topicRepository.getReferenceById(payload.getIdQuestion());
			topic.setIdCategory(payload.getIdCategory());
			topic.setQuestion(payload.getQuestion());
			Long idCategory = topic.getIdCategory();
			String question = topic.getQuestion();
			Long idUser = topic.getIdUser();
			LocalDateTime creationDate = topic.getCreationDate();
			boolean deleted = topic.isDeleted();
			return new TopicDTO(payload.getIdQuestion(), idCategory, question, idUser, creationDate, deleted);
		} catch (Exception e) {
			throw new OwnRuntimeException("Error Updating Question");
		}
	}
}