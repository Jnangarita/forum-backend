package com.forum.app.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateUserInput {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}