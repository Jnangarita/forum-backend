package com.forum.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicDTO;
import com.forum.app.dto.UpdateTopicDTO;
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

	@PutMapping
	public ResponseEntity<TopicDTO> updateTopic(@RequestBody @Valid UpdateTopicDTO payload) {
		TopicDTO topic = topicService.updateTopic(payload);
		return ResponseEntity.ok(topic);
	}

	@GetMapping
	public ResponseEntity<List<Topic>> getTopicList() {
		List<Topic> topicList = topicService.getTopicList();
		return ResponseEntity.ok(topicList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
		topicService.deleteTopic(id);
		return ResponseEntity.noContent().build();
	}
}