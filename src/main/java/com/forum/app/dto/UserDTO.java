package com.forum.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.forum.app.enumeration.GeneralEnum;

import lombok.Data;

@Data
public class UserDTO {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String repeatPassword;

	private String photo = GeneralEnum.BLANK_IMG.getMessageKey();

	@NotNull
	@Min(value = 1, message = "El rol debe ser 1, 2 o 3.")
	@Max(value = 3, message = "El rol debe ser 1, 2 o 3.")
	private Integer role;
}