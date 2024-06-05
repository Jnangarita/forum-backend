package com.forum.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.forum.app.dto.AuthenticateUserDTO;
import com.forum.app.dto.JwtTokenDTO;
import com.forum.app.entity.User;
import com.forum.app.service.impl.TokenService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${spring.data.rest.basePath}/auth/login")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Operation(summary = "Authenticate the user")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody @Valid AuthenticateUserDTO payload) {
		Authentication authToken = new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword());
		var authenticatedUser = authenticationManager.authenticate(authToken);
		var jwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
		return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
	}
}