package com.forum.app.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.utils.Utility;

@Service
public class TokenService {

	private final String tokenKey;
	private final Utility utility;

	public TokenService(@Value("${api.security.token.key}") String tokenKey, Utility utility) {
		this.tokenKey = tokenKey;
		this.utility = utility;
	}

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
				throw new OwnRuntimeException(utility.getMessage("forum.message.error.invalid.token", null));
			}
			return verifier.getSubject();
		} catch (JWTVerificationException exception) {
			String message = utility.getMessage("forum.message.error.validating.token", null);
			throw new JWTVerificationException(message + " " + exception.getMessage());
		}
	}

	private Instant generateExpirationDate(Integer tokenValidTime) {
		return LocalDateTime.now().plusHours(tokenValidTime).toInstant(ZoneOffset.of("-05:00"));
	}
}