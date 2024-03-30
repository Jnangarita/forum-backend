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
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.service.TopicService;

@RestController
@RequestMapping("${spring.data.rest.basePath}/topics")
public class TopicController {
	@Autowired
	private TopicService topicService;

	@PostMapping
	public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody @Valid SaveTopicDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		TopicResponseDTO topic = topicService.createTopic(payload);
		URI url = uriComponentsBuilder.path("api/forum/topics/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(url).body(topic);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicResponseDTO> getTopic(@PathVariable Long id) {
		TopicResponseDTO topic = topicService.getTopic(id);
		return ResponseEntity.ok(topic);
	}

	@PutMapping
	public ResponseEntity<TopicResponseDTO> updateTopic(@RequestBody @Valid UpdateTopicDTO payload) {
		TopicResponseDTO topic = topicService.updateTopic(payload);
		return ResponseEntity.ok(topic);
	}

	@GetMapping
	public ResponseEntity<List<TopicResponseDTO>> getTopicList() {
		List<TopicResponseDTO> topicList = topicService.getTopicList();
		return ResponseEntity.ok(topicList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
		topicService.deleteTopic(id);
		return ResponseEntity.noContent().build();
	}
}