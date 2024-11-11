package com.forum.app.dto.response;

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
	private String userName;

	protected UserDTO(User user) {
		this.email = user.getEmail();
		this.id = user.getId();
		this.userName = user.getProfileName();
	}
}