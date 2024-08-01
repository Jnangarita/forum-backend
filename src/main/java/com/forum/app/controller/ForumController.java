package com.forum.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.forum.app.dto.TopPostDTO;
import com.forum.app.service.ForumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/posts")
public class ForumController {

	private final ForumService forumService;

	public ForumController(ForumService forumService) {
		this.forumService = forumService;
	}

	@Operation(summary = "Get list of posts")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<List<TopPostDTO>> getTopPostList(@RequestParam(required = true) Long id) {
		List<TopPostDTO> topPostList = forumService.getTopPost(id);
		return ResponseEntity.ok(topPostList);
	}
}