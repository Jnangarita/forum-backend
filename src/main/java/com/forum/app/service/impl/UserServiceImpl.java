package com.forum.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.app.dto.BasicUserInfoDTO;
import com.forum.app.dto.IdValueDTO;
import com.forum.app.dto.RoleDTO;
import com.forum.app.dto.UserDTO;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.UserRepository;
import com.forum.app.service.UserService;
import com.forum.app.utils.Utility;

@Service
public class UserServiceImpl implements UserService {

	private Utility utility;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(Utility utility, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.utility = utility;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	@Override
	public UserResponseDTO createUser(UserDTO payload) {
		try {
			LocalDateTime currentDate = LocalDateTime.now();
			User newUser = new User();
			newUser.setFirstName(payload.getFirstName());
			newUser.setLastName(payload.getLastName());
			newUser.setEmail(payload.getEmail());
			newUser.setPassword(passwordEncoder.encode(payload.getPassword()));
			newUser.setRole(payload.getRole());
			newUser.setCreatedAt(currentDate);
			newUser.setDeleted(false);
			User user = userRepository.save(newUser);
			return new UserResponseDTO(user);
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.user", null));
		}
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		try {
			Map<String, Object> user = userRepository.findUserInformationById(id);
			UserResponseDTO dto = new UserResponseDTO();
			dto.setCode(user.get("codigo").toString());
			dto.setCountry(user.get("pais").toString());
			dto.setEmail(user.get("correo_electronico").toString());
			dto.setId(((Number) user.get("id")).longValue());
			dto.setNumberQuestions((Integer) user.get("numero_preguntas"));
			dto.setNumberResponses((Integer) user.get("numero_respuestas"));
			dto.setPhoto(user.get("foto").toString());
			dto.setDeleted((boolean) user.get("eliminado"));
			dto.setUserName(user.get("nombre_usuario").toString());
			RoleDTO userRole = new RoleDTO();
			userRole.setId(((Number) user.get("id_rol")).longValue());
			userRole.setRoleName(user.get("nombre_rol").toString());
			dto.setUserRole(userRole);
			return dto;
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.user", null));
		}
	}

	@Override
	public User findUser(Long id) {
		return userRepository.getReferenceById(id);
	}

	@Transactional
	@Override
	public UserResponseDTO updateUser(Long id, UserDTO payload) {
		try {
			LocalDateTime currentDate = LocalDateTime.now();
			User userToUpdated = findUser(id);
			userToUpdated.setFirstName(payload.getFirstName());
			userToUpdated.setEmail(payload.getEmail());
			userToUpdated.setPassword(passwordEncoder.encode(payload.getPassword()));
			userToUpdated.setUpdatedAt(currentDate);
			User user = userRepository.save(userToUpdated);
			return new UserResponseDTO(user);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.user", null));
		}
	}

	@Override
	public List<BasicUserInfoDTO> getUserList() {
		try {
			List<Map<String, Object>> savedUserList = userRepository.userInfoList();
			List<BasicUserInfoDTO> userList = new ArrayList<>();
			ObjectMapper objectMapper = new ObjectMapper();
			for (Map<String, Object> userMap : savedUserList) {
				Long id = ((Number) userMap.get("id")).longValue();
				String photo = (String) userMap.get("foto");
				String city = (String) userMap.get("pais");
				String userName = (String) userMap.get("nombre_usuario");
				Integer reputation = ((Number) userMap.get("reputacion")).intValue();

				String categoriesJson = (String) userMap.get("categorias");
				List<IdValueDTO> categories = objectMapper.readValue(categoriesJson,
						new TypeReference<List<IdValueDTO>>() {
						});

				BasicUserInfoDTO userDto = new BasicUserInfoDTO(id, photo, city, userName, reputation, categories);
				userList.add(userDto);
			}
			return userList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.user", null));
		}
	}

	@Transactional
	@Override
	public void deleteUser(Long id) {
		try {
			User user = findUser(id);
			if (!user.isDeleted()) {
				user.setDeleted(true);
			}
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.deleting.user", null));
		}
	}
}