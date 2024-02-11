package com.forum.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestDTO {
	private Integer code;
	private String message;
	private List<ErrorDTO> errors;

	public BadRequestDTO(Integer code, String message, List<ErrorDTO> errors) {
		this.code = code;
		this.message = message;
		this.errors = errors;
	}
}