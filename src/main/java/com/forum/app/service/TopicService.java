package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.QuestionResponseDTO;
import com.forum.app.dto.request.TopicInput;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.response.QuestionInfoDTO;
import com.forum.app.entity.Topic;

public interface TopicService {
	TopicResponseDTO createTopic(TopicInput payload);

	QuestionInfoDTO getTopic(Long id);

	TopicResponseDTO updateTopic(Long id, TopicInput payload);

	QuestionListDTO getTopicList();

	void deleteTopic(Long id);

	Topic getTopicById(Long id);

	List<PopularQuestionDTO> getPopularQuestionList();

	List<QuestionResponseDTO> questionListByCategory(String category);
}