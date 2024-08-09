package com.forum.app.dto;

import com.forum.app.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserResponseDTO {
	private String city;
	private String code;
	private String country;
	private boolean deleted;
	private String email;
	private String firstName;
	private Long id;
	private String lastName;
	private Integer numberQuestions;
	private Integer numberResponses;
	private String photo;
	private String userName;
	private RoleDTO userRole;

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
	}
}