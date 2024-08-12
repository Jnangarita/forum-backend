package com.forum.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

	@NotBlank
	private String country;

//	@NotBlank TODO Modificar el dto para que el código del país sea obligatorio
	@Size(max = 3, message = "El código de país debe tener un máximo de 3 caracteres.")
	private String countryCode;

	@NotBlank
	private String city;
}