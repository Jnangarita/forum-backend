package com.forum.app.exception;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.forum.app.dto.BadRequestDTO;
import com.forum.app.dto.ErrorDTO;
import com.forum.app.dto.GeneralErrorDTO;

@RestControllerAdvice
public class TopicExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	Locale locale = LocaleContextHolder.getLocale();

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> entityNotFound(EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BadRequestDTO> emptyOrNullField(MethodArgumentNotValidException e) {
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = messageSource.getMessage("forum.message.error.empty.null.field", null, locale);
		List<ErrorDTO> error = e.getFieldErrors().stream().map(ErrorDTO::new).collect(Collectors.toList());
		BadRequestDTO badRequest = new BadRequestDTO(code, message, error);
		return ResponseEntity.badRequest().body(badRequest);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<GeneralErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		String message = messageSource.getMessage("forum.message.error.foreign.key.constraint", null, locale);
		String description = e.getMostSpecificCause().getMessage();
		GeneralErrorDTO errorDTO = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), message, description);
		return ResponseEntity.badRequest().body(errorDTO);
	}
}