package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
	private Long id;
	private String userName;
	private String email;
	private LocalDateTime createdAt;

	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.userName = user.getFullName();
		this.email = user.getEmail();
		this.createdAt = user.getCreatedAt();
	}
}