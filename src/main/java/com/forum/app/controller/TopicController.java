package com.forum.app.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicDTO;
import com.forum.app.entity.Topic;
import com.forum.app.service.TopicService;

@RestController
@RequestMapping("api/topics")
public class TopicController {
	@Autowired
	private TopicService topicService;

	@PostMapping
	public ResponseEntity<Topic> createTopic(@RequestBody @Valid SaveTopicDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		Topic topic = topicService.createTopic(payload);
		URI url = uriComponentsBuilder.path("api/topics/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(url).body(topic);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicDTO> getTopic(@PathVariable Long id) {
		TopicDTO topic = topicService.getTopic(id);
		return ResponseEntity.ok(topic);
	}
}