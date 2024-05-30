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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.forum.app.dto.BadRequestDTO;
import com.forum.app.dto.ErrorDTO;
import com.forum.app.dto.GeneralErrorDTO;
import com.forum.app.utils.Utility;

@RestControllerAdvice
public class ForumExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	Locale locale = LocaleContextHolder.getLocale();

	@Autowired
	private Utility utility;

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
		String description = utility.getCustomErrorMessage(e);
		GeneralErrorDTO errorDTO = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), message, description);
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<GeneralErrorDTO> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = messageSource.getMessage("forum.message.error.failed.convert.value.type", null, locale);
		String description = messageSource.getMessage("forum.message.error.invalid.format",
				new Object[] { e.getValue() }, locale);
		GeneralErrorDTO badRequest = new GeneralErrorDTO(code, message, description);
		return ResponseEntity.badRequest().body(badRequest);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<GeneralErrorDTO> handleInvalidFormatException(InvalidFormatException e) {
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = messageSource.getMessage("forum.message.error.invalid.data.type", null, locale);
		List<JsonMappingException.Reference> path = e.getPath();
		StringBuilder pathDescription = new StringBuilder();
		for (JsonMappingException.Reference reference : path) {
			if (reference.getFieldName() != null) {
				pathDescription.append(reference.getFieldName());
			}
		}
		String description = messageSource.getMessage("forum.message.error.invalid.value",
				new Object[] { pathDescription }, locale);
		GeneralErrorDTO badRequest = new GeneralErrorDTO(code, message, description);
		return ResponseEntity.badRequest().body(badRequest);
	}
}