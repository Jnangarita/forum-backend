package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.request.IdValueInput;
import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.QuestionOutput;
import com.forum.app.dto.request.TopicInput;
import com.forum.app.dto.TopicOutput;
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
	public TopicOutput createTopic(TopicInput payload) {
		try {
			Topic newQuestion = new Topic();
			setQuestionData(newQuestion, payload);
			Topic topic = topicRepository.save(newQuestion);
			return new TopicOutput(topic);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.question", null));
		}
	}

	private void setQuestionData(Topic topic, TopicInput payload) {
		topic.setCategoryId(payload.getCategoryId());
		topic.setTitleQuestion(payload.getTitleQuestion());
		topic.setQuestion(payload.getQuestion());
		topic.setUserId(payload.getUserId());
		topic.setQuestionStatus(QuestionStatus.UNANSWERED.getStatus());
		topic.setViews(0);
		topic.setVotes(0);
		topic.setSaved(false);
		topic.setLikes(0);
		topic.setDislikes(false);
	}

	@Override
	public QuestionInfoDTO getTopic(Long id) {
		try {
			Map<String, Object> question = topicRepository.getInfoQuestion(id);
			QuestionInfoDTO questionDto = new QuestionInfoDTO();
			setQuestionInfo(questionDto, question);
			return questionDto;
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.question", null));
		}
	}

	private void setQuestionInfo(QuestionInfoDTO dto, Map<String, Object> question) {
		String categoriesJson = (String) question.get(DbColumns.CATEGORIES.getColumns());
		dto.setCreatedAt(utility.getDate(question, DbColumns.CREATION_DATE.getColumns()));
		dto.setDislike((boolean) question.get(DbColumns.DISLIKE.getColumns()));
		dto.setId(utility.convertToLongType(question.get(DbColumns.ID.getColumns())));
		dto.setLike(utility.convertToIntType(question.get(DbColumns.LIKE.getColumns())));
		dto.setUpdatedAt(utility.getDate(question, DbColumns.MODIFICATION_DATE.getColumns()));
		dto.setPhoto((String) question.get(DbColumns.PHOTO.getColumns()));
		dto.setQuestionContent((String) question.get(DbColumns.QUESTION.getColumns()));
		dto.setQuestionTitle((String) question.get(DbColumns.TITLE_QUESTION.getColumns()));
		dto.setReputation(utility.convertToIntType(question.get(DbColumns.REPUTATION.getColumns())));
		dto.setSaved((boolean) question.get(DbColumns.SAVED.getColumns()));
		dto.setUserName((String) question.get(DbColumns.USER_NAME.getColumns()));
		dto.setViews(utility.convertToIntType(question.get(DbColumns.VIEWS.getColumns())));
		dto.setCategories(utility.convertJsonToIdValueDTOList(categoriesJson));
	}

	@Transactional
	@Override
	public TopicOutput updateTopic(Long id, TopicInput payload) {
		try {
			Topic question = getTopicById(id);
			populateQuestion(question, payload);
			return new TopicOutput(question);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.question", null));
		}
	}

	private void populateQuestion(Topic question, TopicInput payload) {
		question.setCategoryId(payload.getCategoryId());
		question.setQuestion(payload.getQuestion());
	}

	@Override
	public QuestionListDTO getTopicList() {
		try {
			Integer totalQuestions = topicRepository.getNumberQuestion();
			List<Map<String, Object>> savedQuestionList = topicRepository.getQuestionList();
			List<QuestionOutput> questionList = populateQuestionList(savedQuestionList);
			return new QuestionListDTO(totalQuestions, questionList);
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.question", null));
		}
	}

	private List<QuestionOutput> populateQuestionList(List<Map<String, Object>> list) {
		List<QuestionOutput> questionList = new ArrayList<>();
		for (Map<String, Object> questionMap : list) {
			QuestionOutput questionDto = new QuestionOutput();
			populateQuestionDto(questionDto, questionMap);
			questionList.add(questionDto);
		}
		return questionList;
	}

	private void populateQuestionDto(QuestionOutput dto, Map<String, Object> question) {
		dto.setAnswers(utility.convertToIntType(question.get(DbColumns.ANSWER.getColumns())));
		dto.setQuestionId(utility.convertToIntType(question.get(DbColumns.QUESTION_ID.getColumns())));
		dto.setQuestionTitle((String) question.get(DbColumns.TITLE_QUESTION.getColumns()));
		dto.setQuestionStatus(((String) question.get(DbColumns.QUESTION_STATUS.getColumns())).charAt(0));
		dto.setCreationDate(utility.getDate(question, DbColumns.CREATION_DATE.getColumns()));
		dto.setUser((String) question.get(DbColumns.USER_NAME.getColumns()));
		dto.setUserId(utility.convertToIntType(question.get(DbColumns.USER_ID.getColumns())));
		dto.setPhoto((String) question.get(DbColumns.PHOTO.getColumns()));
		dto.setViews(utility.convertToIntType(question.get(DbColumns.VIEWS.getColumns())));
		dto.setVotes(utility.convertToIntType(question.get(DbColumns.VOTES.getColumns())));
		String categoriesJson = (String) question.get(DbColumns.CATEGORIES.getColumns());
		List<IdValueInput> categories = utility.convertJsonToIdValueDTOList(categoriesJson);
		dto.setCategories(categories);
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
				PopularQuestionDTO questionDto = new PopularQuestionDTO();
				populatePopularQuestionDto(questionDto, question);
				popularQuestion.add(questionDto);
			}
			return popularQuestion;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.popular.question", null));
		}
	}

	private void populatePopularQuestionDto(PopularQuestionDTO dto, Map<String, Object> question) {
		dto.setId(utility.convertToLongType(question.get(DbColumns.ID.getColumns())));
		dto.setPhoto((String) question.get(DbColumns.PHOTO.getColumns()));
		dto.setQuestionTitle((String) question.get(DbColumns.TITLE_QUESTION.getColumns()));
		dto.setUserName((String) question.get(DbColumns.USER_NAME.getColumns()));
	}

	@Override
	public List<QuestionOutput> questionListByCategory(String category) {
		try {
			List<Map<String, Object>> savedQuestionList = topicRepository.getQuestionByCategory(category);
			return populateQuestionList(savedQuestionList);
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.question", null));
		}
	}
}