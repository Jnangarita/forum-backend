package com.forum.app.service;

import java.util.List;

import javax.validation.Valid;

import com.forum.app.dto.BasicUserInfoDTO;
import com.forum.app.dto.request.ChangePasswordInput;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.request.ResetPasswordInput;
import com.forum.app.dto.request.UpdateUserInput;
import com.forum.app.dto.request.SaveUserInput;
import com.forum.app.dto.UserOutput;
import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.entity.User;

public interface UserService {
	UserInfoDTO createUser(SaveUserInput payload);

	UserOutput getUserById(Long id);

	User findUser(Long id);

	UserInfoDTO updateUser(Long id, UpdateUserInput payload);

	List<BasicUserInfoDTO> getUserList();

	void deleteUser(Long id);

	MessageDTO changePassword(Long id, ChangePasswordInput payload);

	MessageDTO resetPassword(@Valid ResetPasswordInput payload);

	User findUserByEmail(String email);
}