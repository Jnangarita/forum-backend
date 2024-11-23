package com.forum.app.dto.response;

import com.forum.app.dto.response.base.UserDTO;
import com.forum.app.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserInfoDTO extends UserDTO {
	public UserInfoDTO(User user) {
		super(user);
	}
}