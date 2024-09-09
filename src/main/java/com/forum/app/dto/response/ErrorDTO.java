package com.forum.app.dto.response;

import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
	String field;
	String message;

	public ErrorDTO(FieldError error) {
		this.field = error.getField();
		this.message = error.getDefaultMessage();
	}
}