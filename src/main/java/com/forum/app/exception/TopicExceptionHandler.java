package com.forum.app.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TopicExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> entityNotFound(EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}
}