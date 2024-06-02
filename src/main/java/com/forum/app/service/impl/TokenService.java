package com.forum.app.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;

@Service
public class TokenService {
	@Value("${api.security.token.key}")
	private String tokenKey;

	@Autowired
	private MessageSource messageSource;

	Locale locale = LocaleContextHolder.getLocale();

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(tokenKey);
			return JWT.create().withIssuer("forum").withSubject(user.getEmail()).withClaim("id", user.getId())
					.withExpiresAt(generateExpirationDate(2)).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new OwnRuntimeException();
		}
	}

	public String getSubject(String token) {
		DecodedJWT verifier = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(tokenKey);
			verifier = JWT.require(algorithm).withIssuer("forum").build().verify(token);
			if (verifier == null) {
				String message = messageSource.getMessage("forum.message.error.invalid.token", null, locale);
				throw new OwnRuntimeException(message);
			}
			return verifier.getSubject();
		} catch (JWTVerificationException exception) {
			String message = messageSource.getMessage("forum.message.error.validating.token", null, locale);
			throw new JWTVerificationException(message + " " + exception.getMessage());
		}
	}

	private Instant generateExpirationDate(Integer tokenValidTime) {
		return LocalDateTime.now().plusHours(tokenValidTime).toInstant(ZoneOffset.of("-05:00"));
	}
}