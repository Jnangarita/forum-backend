package com.forum.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdValueDTO {
	@NotNull
	private Long id;

	@NotBlank
	private String value;
}