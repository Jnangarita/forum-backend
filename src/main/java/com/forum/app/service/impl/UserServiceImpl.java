package com.forum.app.service.impl;

import com.forum.app.dto.BasicUserInfoOutput;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.UserOutput;
import com.forum.app.dto.request.ChangePasswordInput;
import com.forum.app.dto.request.ResetPasswordInput;
import com.forum.app.dto.request.UserInput;
import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.exception.PasswordException;
import com.forum.app.mapper.UserMapper;
import com.forum.app.repository.UserRepository;
import com.forum.app.service.UserService;
import com.forum.app.utils.Utility;
import com.forum.app.validation.Validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final Utility utility;
	private final UserRepository userRepository;
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	private final Validate validate;
	private final UserMapper userMapper;

	public UserServiceImpl(Utility utility, UserRepository userRepository, JavaMailSender mailSender,
						   Validate validate, TemplateEngine templateEngine, UserMapper userMapper) {
		this.utility = utility;
		this.userRepository = userRepository;
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.validate = validate;
		this.userMapper = userMapper;
	}

	@Transactional
	@Override
	public UserInfoDTO createUser(UserInput payload) {
		log.info(utility.getMessage("forum.message.info.starting.create.user",
				new Object[] { showSecureUserPayload(payload) }));
		try {
			validate.confirmPassword(payload.getPassword(), payload.getRepeatPassword());
			User newUser = userRepository.save(userMapper.convertDtoToEntity(payload));
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

	private String showSecureUserPayload(UserInput payload) {
		if (payload == null) {
			return "{}";
		}
		UserInput securePayload = new UserInput(payload.getEmail(), payload.getFirstName(), payload.getLastName());
		return utility.convertToJsonFormat(securePayload);
	}

	@Override
	public UserOutput getUserById(Long id) {
		log.info(utility.getMessage("forum.message.info.starting.search.user", new Object[] { id }));
		try {
			User user = findUser(id);
			validate.entity(user, id);
			log.info(utility.getMessage("forum.message.info.found.user", new Object[] { id }));
			return userMapper.toUserOutput(user);
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.user", null));
		}
	}

	@Override
	public User findUser(Long id) {
		// TODO: Revisar que si el usuario tiene el id_pais y el id_ciudad en NULL el método va a devolver null
		return userRepository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public UserInfoDTO updateUser(Long id, UserInput payload) {
		log.info("Inicio el proceso para actualizar el usuario con el ID {} y el payload es: \n {}", id, showSecureUserPayload(payload));
		try {
			User userToUpdated = findUser(id);
			validate.entity(userToUpdated, id);
			userMapper.updateUserFromDto(payload, userToUpdated);
			User user = userRepository.save(userToUpdated);
			log.info(utility.getMessage("forum.message.info.updated.user", new Object[] { id }));
			return new UserInfoDTO(user);
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.user", null));
		}
	}

	@Override
	public List<BasicUserInfoOutput> getUserList() {
		log.info(utility.getMessage("forum.message.info.starting.get.user", null));
		try {
			List<User> savedUserList = userRepository.findByDeletedFalse();
			log.info(utility.getMessage("forum.message.info.list.size.user", new Object[] { savedUserList.size() }));
			List<BasicUserInfoOutput> userList = new ArrayList<>();
			for (User user : savedUserList) {
				BasicUserInfoOutput userDto = userMapper.entityToBasicUserInfo(user);
				userList.add(userDto);
			}
			log.info(utility.getMessage("forum.message.info.obtained.list.user", null));
			return userList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getExceptionMsg(e, "forum.message.error.getting.list.user"));
		}
	}

	@Transactional
	@Override
	public void deleteUser(Long id) {
		log.info(utility.getMessage("forum.message.info.delete.user", new Object[] { id }));
		try {
			User user = findUser(id);
			validate.entity(user, id);
			if (!user.isDeleted()) {
				user.setDeleted(true);
				log.info(utility.getMessage("forum.message.info.user.deleted.successfully", new Object[] { id }));
			}
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.deleting.user", null));
		}
	}

	@Transactional
	@Override
	public MessageDTO changePassword(Long id, @Valid ChangePasswordInput payload) {
		log.info(utility.getMessage("forum.message.info.update.password", new Object[] { id }));
		try {
			User user = findUser(id);
			String currentPassword = utility.decodeString(payload.getCurrentPassword());
			String newPassword = utility.decodeString(payload.getNewPassword());
			String confirmPassword = utility.decodeString(payload.getConfirmPassword());
			validate.currentPassword(currentPassword, user.getPassword());
			validate.confirmPassword(newPassword, confirmPassword);
			user.setPassword(utility.encryptPassword(newPassword));
			log.info(utility.getMessage("forum.message.info.update.password.successfully", new Object[] { id }));
			return new MessageDTO(utility.getMessage("forum.message.info.password.updated.successfully", null));
		} catch (PasswordException e) {
			throw e;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.password", null));
		}
	}

	@Transactional
	@Override
	public MessageDTO resetPassword(@Valid ResetPasswordInput payload) {
		log.info(utility.getMessage("forum.message.info.reset.password", new Object[] { payload.getEmail() }));
		try {
			String email = payload.getEmail();
			User user = findUserByEmail(email);
			String newPassword = utility.generatePassword();
			user.setPassword(utility.encryptPassword(newPassword));
			String template = emailTemplate(user.getFirstName(), newPassword);
			sendMail(email, template);
			log.info(utility.getMessage("forum.message.info.update.reset.successfully", new Object[] { email }));
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
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.sending.email", null));
		}
	}
}