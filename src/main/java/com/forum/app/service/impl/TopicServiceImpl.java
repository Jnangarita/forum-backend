package com.forum.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.entity.Topic;
import com.forum.app.enumeration.QuestionStatus;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	@Autowired
	private TopicRepository topicRepository;

	LocalDateTime currentDate = LocalDateTime.now();

	Locale locale = LocaleContextHolder.getLocale();

	@Transactional
	@Override
	public TopicResponseDTO createTopic(SaveTopicDTO payload) {
		Topic newTopic = new Topic();
		newTopic.setIdCategory(payload.getIdCategory());
		newTopic.setQuestion(payload.getQuestion());
		newTopic.setIdUser(payload.getIdUser());
		newTopic.setQuestionStatus(QuestionStatus.UNANSWERED.getStatus());
		newTopic.setCreationDate(currentDate);
		newTopic.setDeleted(false);
		Topic topic = topicRepository.save(newTopic);
		return new TopicResponseDTO(topic);
	}

	@Override
	public TopicResponseDTO getTopic(Long id) {
		Topic topic = topicRepository.getReferenceById(id);
		return new TopicResponseDTO(topic);
	}

	@Transactional
	@Override
	public TopicResponseDTO updateTopic(UpdateTopicDTO payload) {
		Topic topic = topicRepository.getReferenceById(payload.getIdQuestion());
		topic.setIdCategory(payload.getIdCategory());
		topic.setQuestion(payload.getQuestion());
		topic.setModificationDate(currentDate);
		return new TopicResponseDTO(topic);
	}

	@Override
	public List<TopicResponseDTO> getTopicList() {
		List<Topic> listSavedTopics = topicRepository.findByDeletedFalse();
		List<TopicResponseDTO> topicList = new ArrayList<>();
		for (Topic topic : listSavedTopics) {
			TopicResponseDTO topicDto = new TopicResponseDTO(topic);
			topicList.add(topicDto);
		}
		return topicList;
	}

	@Transactional
	@Override
	public void deleteTopic(Long id) {
		Topic topic = topicRepository.getReferenceById(id);
		topic.setDeleted(true);
	}
}