package com.forum.app.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestDTO extends Error {
	private List<ErrorDTO> errors;

	public BadRequestDTO(Integer code, String message, List<ErrorDTO> errors) {
		super(code, message);
		this.errors = errors;
	}
}