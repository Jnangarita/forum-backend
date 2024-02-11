package com.forum.app.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.forum.app.dto.BadRequestDTO;
import com.forum.app.dto.ErrorDTO;

@RestControllerAdvice
public class TopicExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> entityNotFound(EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BadRequestDTO> emptyOrNullField(MethodArgumentNotValidException e) {
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = "The payload is incorrect and can't be processed by the server";
		List<ErrorDTO> error = e.getFieldErrors().stream().map(ErrorDTO::new).collect(Collectors.toList());
		BadRequestDTO badRequest = new BadRequestDTO(code, message, error);
		return ResponseEntity.badRequest().body(badRequest);
	}
}