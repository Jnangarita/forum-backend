package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
	private Long id;
	private String code;
	private String userName;
	private String email;
	private LocalDateTime createdAt;

	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.code = user.getCode();
		this.userName = user.getFirstName() + " " + user.getLastName();
		this.email = user.getEmail();
		this.createdAt = user.getCreatedAt();
	}
}