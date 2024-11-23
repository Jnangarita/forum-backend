package com.forum.app.dto.response.base;

import com.forum.app.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class UserDTO {
	private String email;
	private Long id;
	private String profileName;

	protected UserDTO(User user) {
		this.email = user.getEmail();
		this.id = user.getId();
		this.profileName = user.getProfileName();
	}
}