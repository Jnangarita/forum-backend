package com.forum.app.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;

@Service
public class TokenService {

	@Value("${api.security.token.key}")
	private String tokenKey;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(tokenKey);
			return JWT.create().withIssuer("forum").withSubject(user.getEmail()).withClaim("id", user.getId())
					.withExpiresAt(generateExpirationDate(2)).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new OwnRuntimeException();
		}
	}

	private Instant generateExpirationDate(Integer tokenValidTime) {
		return LocalDateTime.now().plusHours(tokenValidTime).toInstant(ZoneOffset.of("-05:00"));
	}
}