package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.app.dto.BasicUserInfoDTO;
import com.forum.app.dto.ChangePasswordDTO;
import com.forum.app.dto.IdValueDTO;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.ResetPasswordDTO;
import com.forum.app.dto.RoleDTO;
import com.forum.app.dto.UpdateUserDTO;
import com.forum.app.dto.UserDTO;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.exception.PasswordException;
import com.forum.app.repository.UserRepository;
import com.forum.app.service.UserService;
import com.forum.app.utils.Utility;
import com.forum.app.validation.Validate;

@Service
public class UserServiceImpl implements UserService {

	private final Utility utility;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	private final Validate validate;

	public UserServiceImpl(Utility utility, UserRepository userRepository, PasswordEncoder passwordEncoder,
			JavaMailSender mailSender, TemplateEngine templateEngine, Validate validate) {
		this.utility = utility;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.validate = validate;
	}

	@Transactional
	@Override
	public UserInfoDTO createUser(UserDTO payload) {
		try {
			validate.confirmPassword(payload.getPassword(), payload.getRepeatPassword());
			User newUser = new User();
			setUserData(newUser, payload);
			User user = userRepository.save(newUser);
			return new UserInfoDTO(user);
		} catch (PasswordException e) {
			throw e;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.user", null));
		}
	}

	private void setUserData(User user, UserDTO payload) {
		user.setFirstName(payload.getFirstName());
		user.setLastName(payload.getLastName());
		user.setFullName(user.getFirstName() + " " + user.getLastName());
		user.setEmail(payload.getEmail());
		setPasswordData(user, payload.getPassword());
		user.setCountryId(null);
		user.setCityId(null);
		user.setNumberQuestions(0);
		user.setNumberResponses(0);
		user.setRole(payload.getRole());
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		try {
			Map<String, Object> user = userRepository.findUserInformationById(id);
			UserResponseDTO dto = new UserResponseDTO();
			setUserByIdData(dto, user);
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

	private void setUserByIdData(UserResponseDTO dto, Map<String, Object> user) {
		dto.setCode(user.get("codigo").toString());
		dto.setCountry(parseJsonToIdValueDTO((user.get("pais").toString())));
		dto.setCity(parseJsonToIdValueDTO((user.get("ciudad").toString())));
		dto.setEmail(user.get("correo_electronico").toString());
		dto.setId(((Number) user.get("id")).longValue());
		dto.setNumberQuestions((Integer) user.get("numero_preguntas"));
		dto.setNumberResponses((Integer) user.get("numero_respuestas"));
		dto.setPhoto((String) user.get("foto"));
		dto.setDeleted((boolean) user.get("eliminado"));
		dto.setFirstName(user.get("primer_nombre").toString());
		dto.setLastName(user.get("apellido").toString());
		dto.setUserName(user.get("nombre_usuario").toString());
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
	public UserInfoDTO updateUser(Long id, UpdateUserDTO payload) {
		try {
			User userToUpdated = findUser(id);
			updateUserFields(userToUpdated, payload);
			User user = userRepository.save(userToUpdated);
			return new UserInfoDTO(user);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.user", null));
		}
	}

	private void updateUserFields(User user, UpdateUserDTO payload) {
		user.setFirstName(payload.getFirstName());
		user.setLastName(payload.getLastName());
		user.setFullName(payload.getUserName());
		user.setEmail(payload.getEmail());
		user.setCountryId(payload.getCountry().getId());
		user.setCityId(payload.getCity().getId());
	}

	@Override
	public List<BasicUserInfoDTO> getUserList() {
		try {
			List<Map<String, Object>> savedUserList = userRepository.userInfoList();
			List<BasicUserInfoDTO> userList = new ArrayList<>();
			ObjectMapper objectMapper = new ObjectMapper();
			for (Map<String, Object> userMap : savedUserList) {
				BasicUserInfoDTO userDto = setUserListData(objectMapper, userMap);
				userList.add(userDto);
			}
			return userList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getExceptionMsg(e, "forum.message.error.getting.list.user"));
		}
	}

	private BasicUserInfoDTO setUserListData(ObjectMapper objectMapper, Map<String, Object> userMap) {
		try {
			Long id = ((Number) userMap.get("id")).longValue();
			String photo = (String) userMap.get("foto");
			String city = (String) userMap.get("pais");
			String userName = (String) userMap.get("nombre_usuario");
			Integer reputation = ((Number) userMap.get("reputacion")).intValue();
			String categoriesJson = (String) userMap.get("categorias");
			List<IdValueDTO> categories = objectMapper.readValue(categoriesJson, new TypeReference<List<IdValueDTO>>() {
			});
			return new BasicUserInfoDTO(id, photo, city, userName, reputation, categories);
		} catch (JsonProcessingException e) {
			throw new OwnRuntimeException(
					utility.getMessage("forum.message.error.casting.string.to.json.format", null));
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
			validate.currentPassword(payload.getCurrentPassword(), user.getPassword());
			validate.confirmPassword(payload.getNewPassword(), payload.getConfirmPassword());
			setPasswordData(user, payload.getNewPassword());
			return new MessageDTO(utility.getMessage("forum.message.info.password.updated.successfully", null));
		} catch (PasswordException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.password", null));
		}
	}

	private void setPasswordData(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
	}

	@Transactional
	@Override
	public MessageDTO resetPassword(@Valid ResetPasswordDTO payload) {
		try {
			String email = payload.getEmail();
			User user = findUserByEmail(email);
			String newPassword = utility.generatePassword();
			setPasswordData(user, newPassword);
			String template = emailTemplate(user.getFirstName(), newPassword);
			sendMail(email, template);
			return new MessageDTO(utility.getMessage("forum.message.info.password.reset.successfully", null));
		} catch (NullPointerException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getExceptionMsg(e, "forum.message.error.resetting.password"));
		}
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.getUserByEmail(email);
		validate.emptyEmail(user, email);
		return user;
	}

	private String emailTemplate(String userName, String password) {
		Context context = new Context();
		context.setVariable("userName", userName);
		context.setVariable("password", password);
		return templateEngine.process("forgetPassword", context);
	}

	private void sendMail(String email, String content) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setTo(email);
			helper.setSubject(utility.getMessage("forum.message.info.forgot.password", null));
			helper.setText(content, true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.error.sending.email", null));
		}
	}
}