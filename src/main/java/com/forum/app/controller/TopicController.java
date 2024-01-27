package com.forum.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/topics")
public class TopicController {
	@GetMapping
	public String hello() {
		return "Hola mundo!";
	}
}