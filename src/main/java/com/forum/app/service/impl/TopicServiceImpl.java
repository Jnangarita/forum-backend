package com.forum.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.entity.Topic;
import com.forum.app.enumeration.GeneralEnum;
import com.forum.app.enumeration.QuestionStatus;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private TopicRepository topicRepository;

	Locale locale = LocaleContextHolder.getLocale();

	String generalErrorMessage = GeneralEnum.GENERAL_ERROR_MESSAGE.getMessageKey();

	@Transactional
	@Override
	public TopicResponseDTO createTopic(SaveTopicDTO payload) {
		try {
			LocalDateTime currentDate = LocalDateTime.now();
			Topic newTopic = new Topic();
			newTopic.setIdCategory(payload.getIdCategory());
			newTopic.setQuestion(payload.getQuestion());
			newTopic.setIdUser(payload.getIdUser());
			newTopic.setQuestionStatus(QuestionStatus.UNANSWERED.getStatus());
			newTopic.setCreationDate(currentDate);
			newTopic.setDeleted(false);
			Topic topic = topicRepository.save(newTopic);
			return new TopicResponseDTO(topic);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			String message = messageSource.getMessage(generalErrorMessage, null, locale);
			throw new OwnRuntimeException(message);
		}
	}

	@Override
	public TopicResponseDTO getTopic(Long id) {
		try {
			Topic topic = getTopicById(id);
			return new TopicResponseDTO(topic);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			String message = messageSource.getMessage(generalErrorMessage, null, locale);
			throw new OwnRuntimeException(message);
		}
	}

	@Transactional
	@Override
	public TopicResponseDTO updateTopic(UpdateTopicDTO payload) {
		try {
			LocalDateTime currentDate = LocalDateTime.now();
			Topic topic = getTopicById(payload.getIdQuestion());
			topic.setIdCategory(payload.getIdCategory());
			topic.setQuestion(payload.getQuestion());
			topic.setModificationDate(currentDate);
			return new TopicResponseDTO(topic);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			String message = messageSource.getMessage(generalErrorMessage, null, locale);
			throw new OwnRuntimeException(message);
		}
	}

	@Override
	public List<TopicResponseDTO> getTopicList() {
		try {
			List<Topic> listSavedTopics = topicRepository.findByDeletedFalse();
			List<TopicResponseDTO> topicList = new ArrayList<>();
			for (Topic topic : listSavedTopics) {
				TopicResponseDTO topicDto = new TopicResponseDTO(topic);
				topicList.add(topicDto);
			}
			return topicList;
		} catch (Exception e) {
			String message = messageSource.getMessage(generalErrorMessage, null, locale);
			throw new OwnRuntimeException(message);
		}
	}

	@Transactional
	@Override
	public void deleteTopic(Long id) {
		try {
			Topic topic = getTopicById(id);
			if (!topic.isDeleted()) {
				topic.setDeleted(true);
			}
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			String message = messageSource.getMessage(generalErrorMessage, null, locale);
			throw new OwnRuntimeException(message);
		}
	}

	@Override
	public Topic getTopicById(Long id) {
		return topicRepository.getReferenceById(id);
	}
}