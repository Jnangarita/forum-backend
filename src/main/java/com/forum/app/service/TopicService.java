package com.forum.app.service;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.entity.Topic;

public interface TopicService {
	Topic createTopic(SaveTopicDTO payload);

	TopicDTO getTopic(Long id);

	TopicDTO updateTopic(UpdateTopicDTO payload);
}