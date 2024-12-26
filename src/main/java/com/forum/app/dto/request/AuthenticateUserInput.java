package com.forum.app.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthenticateUserInput {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}