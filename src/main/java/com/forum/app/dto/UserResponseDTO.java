package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
	private String code;
	private String country;
	private String email;
	private Long id;
	private Integer numberQuestions;
	private Integer numberResponses;
	private String photo;
	private boolean deleted;
	private String userName;
	private Integer userRole;
	private LocalDateTime createdAt;

	public UserResponseDTO(User user) {
		this.code = user.getCode();
		this.country = user.getCountry();
		this.email = user.getEmail();
		this.id = user.getId();
		this.numberQuestions = user.getNumberQuestions();
		this.numberResponses = user.getNumberResponses();
		this.photo = user.getPhoto();
		this.deleted = user.isDeleted();
		this.userName = user.getFirstName() + " " + user.getLastName();
		this.userRole = user.getRole();
		this.createdAt = user.getCreatedAt();
	}
}