package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.forum.app.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.forum.app.dto.BasicUserInfoOutput;
import com.forum.app.dto.request.ChangePasswordInput;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.request.ResetPasswordInput;
import com.forum.app.dto.request.UpdateUserInput;
import com.forum.app.dto.request.SaveUserInput;
import com.forum.app.dto.UserOutput;
import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.exception.PasswordException;
import com.forum.app.repository.UserRepository;
import com.forum.app.service.UserService;
import com.forum.app.utils.Utility;
import com.forum.app.validation.Validate;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final Utility utility;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	private final Validate validate;
	private final UserMapper userMapper;

	public UserServiceImpl(Utility utility, UserRepository userRepository, PasswordEncoder passwordEncoder,
						   JavaMailSender mailSender, TemplateEngine templateEngine, Validate validate, UserMapper userMapper) {
		this.utility = utility;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.validate = validate;
		this.userMapper = userMapper;
	}

	@Transactional
	@Override
	public UserInfoDTO createUser(SaveUserInput payload) {
		try {
			validate.confirmPassword(payload.getPassword(), payload.getRepeatPassword());
			User user = setUserData(payload);
			User newUser = userRepository.save(user);
			log.info(utility.getMessage("forum.message.info.create.user", new Object[] { newUser.getId() }));
			return new UserInfoDTO(newUser);
		} catch (PasswordException e) {
			throw e;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.user", null));
		}
	}

	private User setUserData(SaveUserInput payload) {
		User user = userMapper.convertDtoToEntity(payload);
		setPasswordData(user, payload.getPassword());
		return user;
	}

	@Override
	public UserOutput getUserById(Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			return userMapper.toUserOutput(user.orElse(null));
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
	public UserInfoDTO updateUser(Long id, UpdateUserInput payload) {
		try {
			User userToUpdated = findUser(id);
			userMapper.updateUserFromDto(payload, userToUpdated);
			User user = userRepository.save(userToUpdated);
			return new UserInfoDTO(user);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.user", null));
		}
	}

	@Override
	public List<BasicUserInfoOutput> getUserList() {
		try {
			List<Map<String, Object>> savedUserList = userRepository.userInfoList();
			List<BasicUserInfoOutput> userList = new ArrayList<>();
			for (Map<String, Object> userMap : savedUserList) {
				BasicUserInfoOutput userDto = new BasicUserInfoOutput();
				setUserListData(userDto, userMap);
				userList.add(userDto);
			}
			return userList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getExceptionMsg(e, "forum.message.error.getting.list.user"));
		}
	}

	private void setUserListData(BasicUserInfoOutput dto, Map<String, Object> userMap) {
/*		dto.setId(utility.convertToLongType(userMap.get(DbColumns.ID.getColumns())));
		dto.setPhoto((String) userMap.get(DbColumns.PHOTO.getColumns()));
		dto.setCity((String) userMap.get(DbColumns.COUNTRY.getColumns()));
		dto.setUserName((String) userMap.get(DbColumns.USER_NAME.getColumns()));
		dto.setReputation(utility.convertToIntType(userMap.get(DbColumns.REPUTATION.getColumns())));
		String categoriesJson = (String) userMap.get(DbColumns.CATEGORIES.getColumns());
		List<IdValueInput> categories = utility.convertJsonToIdValueDTOList(categoriesJson);
		dto.setCategory(categories);*/
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
	public MessageDTO changePassword(Long id, @Valid ChangePasswordInput payload) {
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
	public MessageDTO resetPassword(@Valid ResetPasswordInput payload) {
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