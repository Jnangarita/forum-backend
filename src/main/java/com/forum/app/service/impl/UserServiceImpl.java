package com.forum.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.forum.app.dto.UserDTO;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.UserRepository;
import com.forum.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public UserResponseDTO createUser(UserDTO payload) {
		LocalDateTime currentDate = LocalDateTime.now();
		User newUser = new User();
		newUser.setFullName(payload.getUserName());
		newUser.setEmail(payload.getEmail());
		newUser.setPassword(passwordEncoder.encode(payload.getPassword()));
		newUser.setCreatedAt(currentDate);
		newUser.setDeleted(false);
		User user = userRepository.save(newUser);
		return new UserResponseDTO(user);
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		try {
			User user = findUser(id);
			return new UserResponseDTO(user);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException("Error general");
		}
	}

	@Override
	public User findUser(Long id) {
		return userRepository.getReferenceById(id);
	}

	@Transactional
	@Override
	public UserResponseDTO updateUser(Long id, UserDTO payload) {
		LocalDateTime currentDate = LocalDateTime.now();
		User userToUpdated = findUser(id);
		userToUpdated.setFullName(payload.getUserName());
		userToUpdated.setEmail(payload.getEmail());
		userToUpdated.setPassword(passwordEncoder.encode(payload.getPassword()));
		userToUpdated.setUpdatedAt(currentDate);
		User user = userRepository.save(userToUpdated);
		return new UserResponseDTO(user);
	}

	@Override
	public List<UserResponseDTO> getUserList() {
		List<User> savedUserList = userRepository.findByDeletedFalse();
		List<UserResponseDTO> userList = new ArrayList<>();
		for (User user : savedUserList) {
			UserResponseDTO userDto = new UserResponseDTO(user);
			userList.add(userDto);
		}
		return userList;
	}

	@Transactional
	@Override
	public void deleteUser(Long id) {
		User user = findUser(id);
		if (!user.isDeleted()) {
			user.setDeleted(true);
		}
	}
}