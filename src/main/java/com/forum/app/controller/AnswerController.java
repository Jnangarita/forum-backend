package com.forum.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.AnswerResponseDTO;
import com.forum.app.dto.UpdateAnswerDTO;
import com.forum.app.service.AnswerService;

@RestController
@RequestMapping("${spring.data.rest.basePath}/answers")
public class AnswerController {
	@Autowired
	private AnswerService answerService;

	@Value("${spring.data.rest.basePath}")
	private String basePath;

	@PostMapping
	public ResponseEntity<AnswerResponseDTO> createAnswer(@RequestBody @Valid AnswerDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		AnswerResponseDTO answer = answerService.createAnswer(payload);
		URI url = uriComponentsBuilder.path(basePath + "/answers/{id}").buildAndExpand(answer.getId()).toUri();
		return ResponseEntity.created(url).body(answer);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AnswerResponseDTO> getAnswer(@PathVariable Long id) {
		AnswerResponseDTO answer = answerService.getAnswerById(id);
		return ResponseEntity.ok(answer);
	}

	@PutMapping
	public ResponseEntity<AnswerResponseDTO> updateAnswer(@RequestBody @Valid UpdateAnswerDTO payload) {
		AnswerResponseDTO answer = answerService.updateAnswer(payload);
		return ResponseEntity.ok(answer);
	}

	@GetMapping
	public ResponseEntity<List<AnswerResponseDTO>> getAnswerList() {
		List<AnswerResponseDTO> answerList = answerService.getAnswerList();
		return ResponseEntity.ok(answerList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
		answerService.deleteAnswer(id);
		return ResponseEntity.noContent().build();
	}
}