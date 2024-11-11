package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordInput {
	@NotBlank
	private String email;
}