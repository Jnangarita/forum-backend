package com.forum.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateUserDTO {
	private String email;
	private String password;
}