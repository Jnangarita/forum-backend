package com.forum.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GeneralErrorDTO {
	private Integer code;
	private String message;
	private String description;
}