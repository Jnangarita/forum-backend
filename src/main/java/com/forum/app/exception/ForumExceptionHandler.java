package com.forum.app.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = utility.getMessage("forum.message.error.empty.null.field", null);
		List<ErrorDTO> error = e.getFieldErrors().stream().map(ErrorDTO::new).collect(Collectors.toList());
		BadRequestDTO badRequest = new BadRequestDTO(code, message, error);
		return ResponseEntity.badRequest().body(badRequest);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<GeneralErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		String message = utility.getMessage("forum.message.error.foreign.key.constraint", null);
		String description = utility.getCustomErrorMessage(e);
		GeneralErrorDTO errorDTO = new GeneralErrorDTO(HttpStatus.BAD_REQUEST.value(), message, description);
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<GeneralErrorDTO> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = utility.getMessage("forum.message.error.failed.convert.value.type", null);
		String description = utility.getMessage("forum.message.error.invalid.format", new Object[] { e.getValue() });
		GeneralErrorDTO badRequest = new GeneralErrorDTO(code, message, description);
		return ResponseEntity.badRequest().body(badRequest);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<GeneralErrorDTO> handleInvalidFormatException(InvalidFormatException e) {
		Integer code = HttpStatus.BAD_REQUEST.value();
		String message = utility.getMessage("forum.message.error.invalid.data.type", null);
		List<JsonMappingException.Reference> path = e.getPath();
		StringBuilder pathDescription = new StringBuilder();
		for (JsonMappingException.Reference reference : path) {
			if (reference.getFieldName() != null) {
				pathDescription.append(reference.getFieldName());
			}
		}
		String description = utility.getMessage("forum.message.error.invalid.value", new Object[] { pathDescription });
		GeneralErrorDTO badRequest = new GeneralErrorDTO(code, message, description);
		return ResponseEntity.badRequest().body(badRequest);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<GeneralErrorDTO> handleGenericException(Exception e) {
		Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();
		String message = utility.getMessage("forum.message.error.general", null);
		String description = e.getMessage();
		GeneralErrorDTO internalServerError = new GeneralErrorDTO(code, message, description);
		return ResponseEntity.internalServerError().body(internalServerError);
	}
}