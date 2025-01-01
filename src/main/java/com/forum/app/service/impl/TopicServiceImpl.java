package com.forum.app.service.impl;

import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.QuestionOutput;
import com.forum.app.dto.TopicOutput;
import com.forum.app.dto.request.TopicInput;
import com.forum.app.dto.response.QuestionInfoDTO;
import com.forum.app.entity.Topic;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.mapper.TopicMapper;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.TopicService;
import com.forum.app.utils.Utility;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {
	private final Utility utility;

	private final TopicRepository topicRepository;

	private final TopicMapper topicMapper;

	public TopicServiceImpl(Utility utility, TopicRepository topicRepository, TopicMapper topicMapper) {
		this.utility = utility;
		this.topicRepository = topicRepository;
		this.topicMapper = topicMapper;
	}

	@Transactional
	@Override
	public TopicOutput createTopic(TopicInput payload) {
		try {
			return new TopicOutput(topicRepository.save(topicMapper.topicInputToEntity(payload)));
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.question", null));
		}
	}

	@Override
	public QuestionInfoDTO getTopic(Long id) {
		try {
			return topicMapper.mapToDto(topicRepository.getInfoQuestion(id));
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.question", null));
		}
	}

	@Transactional
	@Override
	public TopicOutput updateTopic(Long id, TopicInput payload) {
		try {
			return new TopicOutput(topicMapper.topicInputToEntityUpdate(payload, getTopicById(id)));
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
			List<QuestionOutput> questionList = populateQuestionList(topicRepository.getTopicList(null));
			return new QuestionListDTO(questionList.size(), questionList);
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.question", null));
		}
	}

	private List<QuestionOutput> populateQuestionList(List<Map<String, Object>> list) {
		List<QuestionOutput> questionList = new ArrayList<>();
		for (Map<String, Object> questionMap : list) {
			questionList.add(topicMapper.mapToQuestionOutput(questionMap));
		}
		return questionList;
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
				popularQuestion.add(topicMapper.mapToPopularQuestionDTO(question));
			}
			return popularQuestion;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.popular.question", null));
		}
	}

	@Override
	public List<QuestionOutput> questionListByCategory(String category) {
		try {
			return populateQuestionList(topicRepository.getTopicList(category));
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.question", null));
		}
	}
}