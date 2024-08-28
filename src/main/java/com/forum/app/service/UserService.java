package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.BasicUserInfoDTO;
import com.forum.app.dto.ChangePasswordDTO;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.UpdateUserDTO;
import com.forum.app.dto.UserDTO;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.entity.User;

public interface UserService {
	UserResponseDTO createUser(UserDTO payload);

	UserResponseDTO getUserById(Long id);

	User findUser(Long id);

	UserResponseDTO updateUser(Long id, UpdateUserDTO payload);

	List<BasicUserInfoDTO> getUserList();

	void deleteUser(Long id);

	MessageDTO changePassword(Long id, ChangePasswordDTO payload);
}