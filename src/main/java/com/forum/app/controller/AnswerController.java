package com.forum.app.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.AnswerResponseDTO;
import com.forum.app.service.AnswerService;

@RestController
@RequestMapping("api/answers")
public class AnswerController {
	@Autowired
	private AnswerService answerService;

	@PostMapping
	public ResponseEntity<AnswerResponseDTO> createTopic(@RequestBody @Valid AnswerDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		AnswerResponseDTO answer = answerService.createAnswer(payload);
		URI url = uriComponentsBuilder.path("api/answers/{id}").buildAndExpand(answer.getId()).toUri();
		return ResponseEntity.created(url).body(answer);
	}
}