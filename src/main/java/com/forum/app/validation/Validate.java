package com.forum.app.validation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.forum.app.entity.User;
import com.forum.app.exception.PasswordException;
import com.forum.app.utils.Utility;

@Component
public class Validate {

	private final Utility utility;
	private final PasswordEncoder passwordEncoder;

	public Validate(Utility utility, PasswordEncoder passwordEncoder) {
		this.utility = utility;
		this.passwordEncoder = passwordEncoder;
	}

	public void currentPassword(String currentPassword, String userPassword) {
		if (!passwordEncoder.matches(currentPassword, userPassword)) {
			throw new PasswordException(utility.getMessage("forum.message.warn.incorrect.password", null));
		}
	}

	public void confirmPassword(String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword)) {
			throw new PasswordException(utility.getMessage("forum.message.warn.password.not.match", null));
		}
	}

	public void emptyEmail(User user, String email) {
		if (user == null) {
			throw new NullPointerException(utility.getMessage("forum.message.warn.null.email", new Object[] { email }));
		}
	}
}