package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.forum.app.dto.TopPostDTO;
import com.forum.app.enumeration.DbColumns;
import com.forum.app.repository.AnswerRepository;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.ForumService;
import com.forum.app.utils.Utility;

@Service
public class ForumServiceImpl implements ForumService {

	private final TopicRepository topicRepository;
	private final AnswerRepository answerRepository;
	private final Utility utility;

	public ForumServiceImpl(TopicRepository topicRepository, AnswerRepository answerRepository, Utility utility) {
		this.topicRepository = topicRepository;
		this.answerRepository = answerRepository;
		this.utility = utility;
	}

	@Override
	public List<TopPostDTO> getTopPost(Long id) {
		List<Map<String, Object>> questionList = topicRepository.findPostedQuestionByUserId(id);
		List<Map<String, Object>> answerList = answerRepository.findPostedAnswersByUserId(id);
		List<TopPostDTO> topPostList = new ArrayList<>();
		topPostList.addAll(convertToTopPostDTOList(questionList, true));
		topPostList.addAll(convertToTopPostDTOList(answerList, false));
		return topPostList;
	}

	private List<TopPostDTO> convertToTopPostDTOList(List<Map<String, Object>> mapList, boolean isQuestion) {
		return mapList.stream().map(data -> mapToTopPostDTO(data, isQuestion)).collect(Collectors.toList());
	}

	private TopPostDTO mapToTopPostDTO(Map<String, Object> data, boolean isQuestion) {
		TopPostDTO dto = new TopPostDTO();
		dto.setPostId(((Number) data.get(DbColumns.ID.getColumns())).longValue());
		dto.setPost(isQuestion ? (String) data.get(DbColumns.QUESTION.getColumns())
				: (String) data.get(DbColumns.ANSWER.getColumns()));
		dto.setUserId(((Number) data.get(DbColumns.USER_ID.getColumns())).longValue());
		dto.setPostType(((String) data.get(DbColumns.TYPE.getColumns())).charAt(0));
		dto.setStatus(isQuestion ? ((String) data.get(DbColumns.QUESTION_STATUS.getColumns())).charAt(0)
				: ((String) data.get(DbColumns.ANSWER_STATUS.getColumns())).charAt(0));
		dto.setCreationDate(utility.getDate(data, DbColumns.CREATION_DATE.getColumns()));
		return dto;
	}
}