package com.forum.app.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String userName;

	@NotBlank
	@Email
	private String email;

	@Valid
	private IdValueDTO country;

	@Valid
	private IdValueDTO city;
}