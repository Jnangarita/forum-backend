package com.forum.app.dto.request;

import javax.validation.constraints.*;

import com.forum.app.dto.request.groups.CreateGroup;
import com.forum.app.dto.request.groups.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {
	@NotNull(groups = {UpdateGroup.class})
	private Long cityId;

	@NotNull(groups = {UpdateGroup.class})
	private Long countryId;

	@Email
	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String email;

	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String firstName;

	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String lastName;

	@NotBlank(groups = {CreateGroup.class})
	private String password;

	@NotBlank(groups = {UpdateGroup.class})
	private String profileName;

	@NotBlank(groups = {CreateGroup.class})
	private String repeatPassword;

	@NotNull(groups = {CreateGroup.class})
	@Min(value = 1, message = "El rol debe ser 1, 2 o 3.")
	@Max(value = 3, message = "El rol debe ser 1, 2 o 3.")
	private Long roleId;
}