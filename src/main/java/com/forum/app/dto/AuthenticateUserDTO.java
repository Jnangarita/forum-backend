package com.forum.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateUserDTO {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}