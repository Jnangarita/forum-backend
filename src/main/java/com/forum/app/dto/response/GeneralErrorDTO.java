package com.forum.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralErrorDTO extends Error {
	private String description;

	public GeneralErrorDTO(Integer code, String message, String description) {
		super(code, message);
		this.description = description;
	}
}