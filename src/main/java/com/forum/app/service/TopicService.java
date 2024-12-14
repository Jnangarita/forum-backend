package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.QuestionOutput;
import com.forum.app.dto.request.TopicInput;
import com.forum.app.dto.TopicOutput;
import com.forum.app.dto.response.QuestionInfoDTO;
import com.forum.app.entity.Topic;

public interface TopicService {
	TopicOutput createTopic(TopicInput payload);

	QuestionInfoDTO getTopic(Long id);

	TopicOutput updateTopic(Long id, TopicInput payload);

	QuestionListDTO getTopicList();

	void deleteTopic(Long id);

	Topic getTopicById(Long id);

	List<PopularQuestionDTO> getPopularQuestionList();

	List<QuestionOutput> questionListByCategory(String category);
}