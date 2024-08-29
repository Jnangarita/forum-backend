package com.forum.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.app.dto.BasicUserInfoDTO;
import com.forum.app.dto.ChangePasswordDTO;
import com.forum.app.dto.IdValueDTO;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.RoleDTO;
import com.forum.app.dto.UpdateUserDTO;
import com.forum.app.dto.UserDTO;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.exception.PasswordNotMatchException;
import com.forum.app.exception.IncorrectPasswordException;
import com.forum.app.repository.UserRepository;
import com.forum.app.service.UserService;
import com.forum.app.utils.Utility;

@Service
public class UserServiceImpl implements UserService {

	private final Utility utility;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

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
			User newUser = setUserData(payload, currentDate);
			User user = userRepository.save(newUser);
			return new UserResponseDTO(user);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.user", null));
		}
	}

	private User setUserData(UserDTO payload, LocalDateTime currentDate) {
		User newUser = new User();
		String firstName = payload.getFirstName();
		String lastName = payload.getLastName();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setFullName(firstName + " " + lastName);
		newUser.setEmail(payload.getEmail());
		newUser.setPassword(passwordEncoder.encode(payload.getPassword()));
		newUser.setCountryId(null);
		newUser.setCityId(null);
		newUser.setNumberQuestions(0);
		newUser.setNumberResponses(0);
		newUser.setPhoto(payload.getPhoto());
		newUser.setRole(payload.getRole());
		newUser.setCreatedAt(currentDate);
		newUser.setDeleted(false);
		return newUser;
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		try {
			Map<String, Object> user = userRepository.findUserInformationById(id);
			UserResponseDTO dto = new UserResponseDTO();
			dto.setCode(user.get("codigo").toString());
			dto.setCountry(parseJsonToIdValueDTO((user.get("pais").toString())));
			dto.setCity(parseJsonToIdValueDTO((user.get("ciudad").toString())));
			dto.setEmail(user.get("correo_electronico").toString());
			dto.setId(((Number) user.get("id")).longValue());
			dto.setNumberQuestions((Integer) user.get("numero_preguntas"));
			dto.setNumberResponses((Integer) user.get("numero_respuestas"));
			dto.setPhoto(user.get("foto").toString());
			dto.setDeleted((boolean) user.get("eliminado"));
			dto.setFirstName(user.get("primer_nombre").toString());
			dto.setLastName(user.get("apellido").toString());
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

	private IdValueDTO parseJsonToIdValueDTO(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(jsonString, new TypeReference<IdValueDTO>() {
			});
		} catch (JsonProcessingException e) {
			throw new OwnRuntimeException(
					utility.getMessage("forum.message.error.casting.string.to.json.format", null));
		}
	}

	@Override
	public User findUser(Long id) {
		return userRepository.getReferenceById(id);
	}

	@Transactional
	@Override
	public UserResponseDTO updateUser(Long id, UpdateUserDTO payload) {
		try {
			LocalDateTime currentDate = LocalDateTime.now();
			User userToUpdated = findUser(id);
			userToUpdated.setFirstName(payload.getFirstName());
			userToUpdated.setLastName(payload.getLastName());
			userToUpdated.setFullName(payload.getUserName());
			userToUpdated.setEmail(payload.getEmail());
			userToUpdated.setCountryId(payload.getCountry().getId());
			userToUpdated.setCityId(payload.getCity().getId());
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

	@Transactional
	@Override
	public MessageDTO changePassword(Long id, @Valid ChangePasswordDTO payload) {
		try {
			User user = findUser(id);
			if (!passwordEncoder.matches(payload.getCurrentPassword(), user.getPassword())) {
				throw new IncorrectPasswordException();
			}
			if (!payload.getNewPassword().equals(payload.getConfirmPassword())) {
				throw new PasswordNotMatchException();
			}
			setPasswordData(user, payload.getNewPassword());
			String msg = utility.getMessage("forum.message.info.password.updated.successfully", null);
			return new MessageDTO(msg);
		} catch (IncorrectPasswordException | PasswordNotMatchException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.password", null));
		}
	}

	private void setPasswordData(User user, String newPassword) {
		LocalDateTime currentDate = LocalDateTime.now();
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setUpdatedAt(currentDate);
	}
}