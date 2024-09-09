package com.forum.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Error {
	private Integer code;
	private String message;

	protected Error(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}