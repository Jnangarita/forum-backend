package com.forum.app.controller;

import java.net.URI;
import java.util.List;

import com.forum.app.dto.request.groups.CreateGroup;
import com.forum.app.dto.request.groups.UpdateGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.forum.app.dto.request.AnswerInput;
import com.forum.app.dto.response.AnswerOutput;
import com.forum.app.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/answers")
@SecurityRequirement(name = "bearer-key")
public class AnswerController {

	private final AnswerService answerService;
	private final String basePath;

	public AnswerController(AnswerService answerService, @Value("${spring.data.rest.basePath}") String basePath) {
		this.answerService = answerService;
		this.basePath = basePath;
	}

	@Operation(summary = "Save an answer")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AnswerOutput> createAnswer(
			@RequestBody @Validated({CreateGroup.class}) AnswerInput payload,
			UriComponentsBuilder uriComponentsBuilder) {
		AnswerOutput answer = answerService.createAnswer(payload);
		URI url = uriComponentsBuilder.path(basePath + "/answers/{id}").buildAndExpand(answer.getId()).toUri();
		return ResponseEntity.created(url).body(answer);
	}

	@Operation(summary = "Gets an answer by id")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AnswerOutput> getAnswer(
			@Parameter(description = "Id of the answer to search") @PathVariable Long id) {
		AnswerOutput answer = answerService.getAnswerById(id);
		return ResponseEntity.ok(answer);
	}

	@Operation(summary = "Update an answer")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AnswerOutput> updateAnswer(
			@Parameter(description = "Id of the answer to be updated") @PathVariable Long id,
			@RequestBody @Validated({UpdateGroup.class}) AnswerInput payload) {
		AnswerOutput answer = answerService.updateAnswer(id, payload);
		return ResponseEntity.ok(answer);
	}

	@Operation(summary = "Get the list of answers")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AnswerOutput>> getAnswerList() {
		List<AnswerOutput> answerList = answerService.getAnswerList();
		return ResponseEntity.ok(answerList);
	}

	@Operation(summary = "Delete an answer")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteAnswer(
			@Parameter(description = "Id of the answer to search") @PathVariable Long id) {
		answerService.deleteAnswer(id);
		return ResponseEntity.noContent().build();
	}
}