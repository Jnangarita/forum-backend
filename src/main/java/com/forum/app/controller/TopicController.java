package com.forum.app.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.QuestionListDTO;
import com.forum.app.dto.SaveTopicDTO;
import com.forum.app.dto.TopicResponseDTO;
import com.forum.app.dto.UpdateTopicDTO;
import com.forum.app.service.TopicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

	private final TopicService topicService;
	private final String basePath;

	public TopicController(TopicService topicService, @Value("${spring.data.rest.basePath}") String basePath) {
		this.topicService = topicService;
		this.basePath = basePath;
	}

	@Operation(summary = "Save a question")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody @Valid SaveTopicDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		TopicResponseDTO topic = topicService.createTopic(payload);
		URI url = uriComponentsBuilder.path(basePath + "/topics/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(url).body(topic);
	}

	@Operation(summary = "Gets a question by id")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TopicResponseDTO> getTopic(
			@Parameter(description = "Id of the question to search") @PathVariable Long id) {
		TopicResponseDTO topic = topicService.getTopic(id);
		return ResponseEntity.ok(topic);
	}

	@Operation(summary = "Update a question")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TopicResponseDTO> updateTopic(@RequestBody @Valid UpdateTopicDTO payload) {
		TopicResponseDTO topic = topicService.updateTopic(payload);
		return ResponseEntity.ok(topic);
	}

	@Operation(summary = "Get the list of questions")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<QuestionListDTO> getTopicList() {
		QuestionListDTO topicList = topicService.getTopicList();
		return ResponseEntity.ok(topicList);
	}

	@Operation(summary = "Delete a question")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteTopic(
			@Parameter(description = "Id of the question to search") @PathVariable Long id) {
		topicService.deleteTopic(id);
		return ResponseEntity.noContent().build();
	}
}