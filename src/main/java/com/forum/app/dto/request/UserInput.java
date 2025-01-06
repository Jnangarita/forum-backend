package com.forum.app.dto.request;

import com.forum.app.dto.request.groups.CreateGroup;
import com.forum.app.dto.request.groups.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	public UserInput(String email, String firstName, String lastName) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}