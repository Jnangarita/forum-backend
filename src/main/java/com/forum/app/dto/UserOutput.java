package com.forum.app.dto;

import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.entity.City;
import com.forum.app.entity.Country;
import com.forum.app.entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserOutput extends UserInfoDTO {
	private City city;
	private String code;
	private Country country;
	private boolean deleted;
	private String firstName;
	private String lastName;
	private Integer numberQuestions;
	private Integer numberResponses;
	private String photo;
	private Role role;
}