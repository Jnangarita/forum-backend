package com.forum.app.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.forum.app.dto.request.base.UserBaseInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserInput extends UserBaseInput {
	@NotBlank
	private String password;

	@NotBlank
	private String repeatPassword;

	@NotNull
	@Min(value = 1, message = "El rol debe ser 1, 2 o 3.")
	@Max(value = 3, message = "El rol debe ser 1, 2 o 3.")
	private Integer role;
}