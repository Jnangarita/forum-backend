package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.entity.Topic;

public interface TopicService {
	TopicResponseDTO createTopic(SaveTopicDTO payload);

	TopicResponseDTO getTopic(Long id);

	TopicResponseDTO updateTopic(UpdateTopicDTO payload);

	List<TopicResponseDTO> getTopicList();

	void deleteTopic(Long id);

	Topic getTopicById(Long id);
}