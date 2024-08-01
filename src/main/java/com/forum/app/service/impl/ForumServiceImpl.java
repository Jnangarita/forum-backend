package com.forum.app.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.forum.app.dto.TopPostDTO;
import com.forum.app.repository.AnswerRepository;
import com.forum.app.repository.TopicRepository;
import com.forum.app.service.ForumService;

@Service
public class ForumServiceImpl implements ForumService {

	private TopicRepository topicRepository;
	private AnswerRepository answerRepository;

	public ForumServiceImpl(TopicRepository topicRepository, AnswerRepository answerRepository) {
		this.topicRepository = topicRepository;
		this.answerRepository = answerRepository;
	}

	@Override
	public List<TopPostDTO> getTopPost(Long id) {
		List<Map<String, Object>> questionList = topicRepository.findPostedQuestionByUserId(id);
		List<Map<String, Object>> answerList = answerRepository.findPostedAnswersByUserId(id);
		List<TopPostDTO> topPostList = new ArrayList<>();

		topPostList.addAll(
				questionList.stream().map(question -> mapToTopPostDTO(question, true)).collect(Collectors.toList()));

		topPostList
				.addAll(answerList.stream().map(answer -> mapToTopPostDTO(answer, false)).collect(Collectors.toList()));

		return topPostList;
	}

	private TopPostDTO mapToTopPostDTO(Map<String, Object> data, boolean isQuestion) {
		Long postId = ((Number) data.get("id")).longValue();
		String post = isQuestion ? (String) data.get("pregunta") : (String) data.get("respuesta");
		Long userId = ((Number) data.get("id_usuario")).longValue();
		char postType = ((String) data.get("tipo")).charAt(0);
		char status = isQuestion ? ((String) data.get("estado_pregunta")).charAt(0)
				: ((String) data.get("estado_respuesta")).charAt(0);
		LocalDateTime creationDate = ((Timestamp) data.get("fecha_creacion")).toLocalDateTime();

		return new TopPostDTO(creationDate, postId, post, postType, status, userId);
	}
}