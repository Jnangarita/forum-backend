package com.forum.app.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.forum.app.dto.request.base.UserBaseInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInput extends UserBaseInput {
	@NotBlank
	private String profileName;

	@Valid
	private IdValueInput country;

	@Valid
	private IdValueInput city;
}