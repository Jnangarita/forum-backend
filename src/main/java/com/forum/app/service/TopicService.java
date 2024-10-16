package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.QuestionResponseDTO;
import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.dto.response.QuestionInfoDTO;
import com.forum.app.entity.Topic;

public interface TopicService {
	TopicResponseDTO createTopic(SaveTopicDTO payload);

	QuestionInfoDTO getTopic(Long id);

	TopicResponseDTO updateTopic(Long id, UpdateTopicDTO payload);

	QuestionListDTO getTopicList();

	void deleteTopic(Long id);

	Topic getTopicById(Long id);

	List<PopularQuestionDTO> getPopularQuestionList();

	List<QuestionResponseDTO> questionListByCategory(String category);
}