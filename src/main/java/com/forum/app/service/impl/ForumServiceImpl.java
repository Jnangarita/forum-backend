package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.forum.app.entity.Answer;
import com.forum.app.entity.Topic;
import com.forum.app.mapper.AnswerMapper;
import com.forum.app.mapper.TopicMapper;
import org.springframework.stereotype.Service;

import com.forum.app.dto.TopPostDTO;
import com.forum.app.repository.AnswerRepository;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.ForumService;

@Service
public class ForumServiceImpl implements ForumService {

	private final TopicRepository topicRepository;
	private final AnswerRepository answerRepository;
	private final TopicMapper topicMapper;
	private final AnswerMapper answerMapper;

	public ForumServiceImpl(TopicRepository topicRepository, AnswerRepository answerRepository, TopicMapper topicMapper, AnswerMapper answerMapper) {
		this.topicRepository = topicRepository;
		this.answerRepository = answerRepository;
		this.topicMapper = topicMapper;
		this.answerMapper = answerMapper;
	}

	@Override
	public List<TopPostDTO> getTopPost(Long id) {
		List<Topic> questionList = topicRepository.findByDeletedFalseAndUserId(id);
		List<Answer> answerList = answerRepository.findByDeletedFalseAndUserId(id);
		List<TopPostDTO> topPostList = new ArrayList<>();
		topPostList.addAll(convertToTopPostDTOList(questionList, true));
		topPostList.addAll(convertToTopPostDTOList(answerList, false));
		return topPostList;
	}

	private List<TopPostDTO> convertToTopPostDTOList(List<?> entityList, boolean isQuestion) {
		return entityList.stream().map(entity -> mapToTopPostDTO(entity, isQuestion)).collect(Collectors.toList());
	}

	private TopPostDTO mapToTopPostDTO(Object entity, boolean isQuestion) {
		TopPostDTO dto;
		if (isQuestion) {
			Topic topic = (Topic) entity;
			dto = topicMapper.convertTopicEntityToTopPost(topic);
		} else {
			Answer answer = (Answer) entity;
			dto = answerMapper.convertAnswerEntityToTopPost(answer);
		}
		return dto;
	}
}