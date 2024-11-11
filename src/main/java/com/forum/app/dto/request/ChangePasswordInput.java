package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordInput {
	@NotBlank
	private String currentPassword;

	@NotBlank
	private String newPassword;

	@NotBlank
	private String confirmPassword;
}