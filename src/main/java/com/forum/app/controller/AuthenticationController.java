package com.forum.app.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.forum.app.dto.request.AuthenticateUserInput;
import com.forum.app.dto.JwtTokenDTO;
import com.forum.app.entity.User;
import com.forum.app.service.impl.TokenService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${spring.data.rest.basePath}/auth/login")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@Operation(summary = "Authenticate user")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody @Valid AuthenticateUserInput payload) {
		Authentication authToken = new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword());
		var authenticatedUser = authenticationManager.authenticate(authToken);
		var jwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
		return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
	}
}