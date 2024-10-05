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
import com.forum.app.dto.response.QuestionInfoDTO;
import com.forum.app.entity.Topic;
import com.forum.app.enumeration.DbColumns;
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
			Topic newQuestion = setQuestionData(payload);
			Topic topic = topicRepository.save(newQuestion);
			return new TopicResponseDTO(topic);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.question", null));
		}
	}

	private Topic setQuestionData(SaveTopicDTO payload) {
		Topic newTopic = new Topic();
		newTopic.setCategoryId(payload.getCategoryId());
		newTopic.setTitleQuestion(payload.getTitleQuestion());
		newTopic.setQuestion(payload.getQuestion());
		newTopic.setUserId(payload.getUserId());
		newTopic.setQuestionStatus(QuestionStatus.UNANSWERED.getStatus());
		return newTopic;
	}

	@Override
	public QuestionInfoDTO getTopic(Long id) {
		try {
			Map<String, Object> question = topicRepository.getInfoQuestion(id);
			ObjectMapper objectMapper = new ObjectMapper();
			String categoriesJson = (String) question.get("categorias");
			LocalDateTime creationDate = getDate(question, DbColumns.CREATION_DATE.getColumns());
			boolean dislike = (boolean) question.get("no_me_gusta");
			Long questionId = ((Number) question.get("id")).longValue();
			Integer likes = ((Number) question.get("me_gusta")).intValue();
			LocalDateTime modificationDate = getDate(question, "fecha_modificacion");
			String photo = (String) question.get(DbColumns.PHOTO.getColumns());
			String content = (String) question.get("pregunta");
			String title = (String) question.get(DbColumns.TITLE_QUESTION.getColumns());
			Integer reputation = ((Number) question.get("reputacion")).intValue();
			boolean saved = (boolean) question.get("guardado");
			String userName = (String) question.get(DbColumns.USER_NAME.getColumns());
			Integer views = ((Number) question.get(DbColumns.VIEWS.getColumns())).intValue();
			List<IdValueDTO> categories = objectMapper.readValue(categoriesJson, new TypeReference<List<IdValueDTO>>() {
			});
			return new QuestionInfoDTO(categories, creationDate, dislike, questionId, likes, photo, content, title,
					reputation, saved, modificationDate, userName, views);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.question", null));
		}
	}

	private LocalDateTime getDate(Map<String, Object> result, String column) {
		return result.get(column) != null ? ((Timestamp) result.get(column)).toLocalDateTime() : null;
	}

	@Transactional
	@Override
	public TopicResponseDTO updateTopic(Long id, UpdateTopicDTO payload) {
		try {
			Topic question = getTopicById(id);
			question.setCategoryId(payload.getCategoryId());
			question.setQuestion(payload.getQuestion());
			return new TopicResponseDTO(question);
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
				String questionTitle = (String) questionMap.get(DbColumns.TITLE_QUESTION.getColumns());
				String questionStatus = (String) questionMap.get("estado_pregunta");
				LocalDateTime creationDate = getDate(questionMap, DbColumns.CREATION_DATE.getColumns());
				String userName = (String) questionMap.get(DbColumns.USER_NAME.getColumns());
				Integer userId = ((Number) questionMap.get("id_usuario")).intValue();
				String photo = (String) questionMap.get(DbColumns.PHOTO.getColumns());
				Integer views = ((Number) questionMap.get(DbColumns.VIEWS.getColumns())).intValue();
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
				String photo = (String) question.get(DbColumns.PHOTO.getColumns());
				String questionTitle = (String) question.get(DbColumns.TITLE_QUESTION.getColumns());
				String userName = (String) question.get(DbColumns.USER_NAME.getColumns());
				PopularQuestionDTO questionDto = new PopularQuestionDTO(id, photo, questionTitle, userName);
				popularQuestion.add(questionDto);
			}
			return popularQuestion;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.popular.question", null));
		}
	}
}