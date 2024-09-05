package com.forum.app.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.app.dto.IdValueDTO;
import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.QuestionResponseDTO;
import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.entity.Topic;
import com.forum.app.enumeration.QuestionStatus;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.TopicService;
import com.forum.app.utils.Utility;

@Service
public class TopicServiceImpl implements TopicService {

	private final Utility utility;
	private final TopicRepository topicRepository;

	public TopicServiceImpl(Utility utility, TopicRepository topicRepository) {
		this.utility = utility;
		this.topicRepository = topicRepository;
	}

	@Transactional
	@Override
	public TopicResponseDTO createTopic(SaveTopicDTO payload) {
		try {
			Topic newTopic = new Topic();
			newTopic.setIdCategory(payload.getIdCategory());
			newTopic.setQuestion(payload.getQuestion());
			newTopic.setIdUser(payload.getIdUser());
			newTopic.setQuestionStatus(QuestionStatus.UNANSWERED.getStatus());
			newTopic.setCreationDate(LocalDateTime.now());
			newTopic.setDeleted(false);
			Topic topic = topicRepository.save(newTopic);
			return new TopicResponseDTO(topic);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.question", null));
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
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.question", null));
		}
	}

	@Transactional
	@Override
	public TopicResponseDTO updateTopic(UpdateTopicDTO payload) {
		try {
			Topic topic = getTopicById(payload.getIdQuestion());
			topic.setIdCategory(payload.getIdCategory());
			topic.setQuestion(payload.getQuestion());
			topic.setModificationDate(LocalDateTime.now());
			return new TopicResponseDTO(topic);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.question", null));
		}
	}

	@Override
	public QuestionListDTO getTopicList() {
		try {
			Integer totalQuestions = topicRepository.getNumberQuestion();
			List<Map<String, Object>> savedQuestionList = topicRepository.getQuestionList();
			List<QuestionResponseDTO> questionList = new ArrayList<>();
			ObjectMapper objectMapper = new ObjectMapper();
			for (Map<String, Object> questionMap : savedQuestionList) {
				Integer totalAnswer = ((Number) questionMap.get("respuestas")).intValue();
				Integer questionId = ((Number) questionMap.get("id_pregunta")).intValue();
				String questionTitle = (String) questionMap.get("titulo_pregunta");
				String questionStatus = (String) questionMap.get("estado_pregunta");
				LocalDateTime creationDate = ((Timestamp) questionMap.get("fecha_creacion")).toLocalDateTime();
				String userName = (String) questionMap.get("nombre_usuario");
				Integer userId = ((Number) questionMap.get("id_usuario")).intValue();
				String photo = (String) questionMap.get("foto");
				Integer views = ((Number) questionMap.get("vistas")).intValue();
				Integer votes = ((Number) questionMap.get("votos")).intValue();

				String categoriesJson = (String) questionMap.get("lista_categoria");
				List<IdValueDTO> categories = objectMapper.readValue(categoriesJson,
						new TypeReference<List<IdValueDTO>>() {
						});
				QuestionResponseDTO questionDto = new QuestionResponseDTO(totalAnswer, categories, photo, questionId,
						questionStatus, questionTitle, creationDate, userName, userId, views, votes);
				questionList.add(questionDto);
			}
			return new QuestionListDTO(totalQuestions, questionList);
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.question", null));
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
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.deleting.question", null));
		}
	}

	@Override
	public Topic getTopicById(Long id) {
		return topicRepository.getReferenceById(id);
	}

	@Override
	public List<PopularQuestionDTO> getPopularQuestionList() {
		try {
			List<Map<String, Object>> questionList = topicRepository.getPopularQuestion();
			List<PopularQuestionDTO> popularQuestion = new ArrayList<>();
			for (Map<String, Object> question : questionList) {
				Long id = ((Number) question.get("id")).longValue();
				String photo = (String) question.get("foto");
				String questionTitle = (String) question.get("titulo_pregunta");
				String userName = (String) question.get("nombre_usuario");
				PopularQuestionDTO questionDto = new PopularQuestionDTO(id, photo, questionTitle, userName);
				popularQuestion.add(questionDto);
			}
			return popularQuestion;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.popular.question", null));
		}
	}
}