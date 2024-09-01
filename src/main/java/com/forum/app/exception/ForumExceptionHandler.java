package com.forum.app.exception;

import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.forum.app.dto.BadRequestDTO;
import com.forum.app.dto.ErrorDTO;
import com.forum.app.dto.GeneralErrorDTO;
import com.forum.app.utils.Utility;

@RestControllerAdvice
public class ForumExceptionHandler {

	private final Utility utility;

	public ForumExceptionHandler(Utility utility) {
		this.utility = utility;
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> entityNotFound(EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BadRequestDTO> emptyOrNullField(MethodArgumentNotValidException e) {
		return ResponseEntity.badRequest()
				.body(new BadRequestDTO(HttpStatus.BAD_REQUEST.value(),
						utility.getMessage("forum.message.error.empty.null.field", null),
						e.getFieldErrors().stream().map(ErrorDTO::new).collect(Collectors.toList())));
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<BadRequestDTO> handleBindException(BindException e) {
		return ResponseEntity.badRequest()
				.body(new BadRequestDTO(HttpStatus.BAD_REQUEST.value(),
						utility.getMessage("forum.message.error.empty.null.field", null),
						e.getFieldErrors().stream().map(ErrorDTO::new).collect(Collectors.toList())));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<GeneralErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		return ResponseEntity.badRequest()
				.body(new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(),
						utility.getMessage("forum.message.error.foreign.key.constraint", null),
						utility.getCustomErrorMessage(e)));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<GeneralErrorDTO> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		return ResponseEntity.badRequest()
				.body(new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(),
						utility.getMessage("forum.message.error.failed.convert.value.type", null),
						utility.getMessage("forum.message.error.invalid.format", new Object[] { e.getValue() })));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<GeneralErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		StringBuilder pathDescription = new StringBuilder();
		if (e.getCause() instanceof InvalidFormatException) {
			InvalidFormatException cause = (InvalidFormatException) e.getCause();
			for (JsonMappingException.Reference reference : cause.getPath()) {
				if (reference.getFieldName() != null) {
					pathDescription.append(reference.getFieldName());
				}
			}
		}
		return ResponseEntity.badRequest().body(new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(),
				utility.getMessage("forum.message.error.invalid.data.type", null),
				utility.getMessage("forum.message.error.invalid.value", new Object[] { pathDescription.toString() })));
	}

	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<GeneralErrorDTO> handlePasswordException(PasswordException e) {
		return ResponseEntity.badRequest().body(new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(),
				utility.getMessage("forum.message.warn.password", null), e.getMessage()));
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<GeneralErrorDTO> handlePasswordException(AuthenticationException e) {
		return ResponseEntity.badRequest()
				.body(new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(),
						utility.getMessage("forum.message.error.logging", null),
						utility.getMessage("forum.message.error.invalid.credentials", null)));
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<GeneralErrorDTO> handleNullPointerException(Exception e) {
		return ResponseEntity.internalServerError().body(new GeneralErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				utility.getMessage("forum.message.error.general", null), e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<GeneralErrorDTO> handleGenericException(Exception e) {
		return ResponseEntity.internalServerError().body(new GeneralErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				utility.getMessage("forum.message.error.general", null), e.getMessage()));
	}
}