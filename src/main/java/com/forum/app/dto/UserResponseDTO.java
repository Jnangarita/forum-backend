package com.forum.app.dto;

import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserResponseDTO extends UserInfoDTO {
	private IdValueDTO city;
	private String code;
	private IdValueDTO country;
	private boolean deleted;
	private String firstName;
	private String lastName;
	private Integer numberQuestions;
	private Integer numberResponses;
	private String photo;
	private RoleDTO userRole;

	public UserResponseDTO(User user) {
		super(user);
		this.code = user.getCode();
		this.numberQuestions = user.getNumberQuestions();
		this.numberResponses = user.getNumberResponses();
		this.deleted = user.isDeleted();
	}
}