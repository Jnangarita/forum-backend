package com.forum.app.controller;

import com.forum.app.dto.JwtTokenDTO;
import com.forum.app.dto.request.AuthenticateUserInput;
import com.forum.app.entity.User;
import com.forum.app.service.impl.TokenService;
import com.forum.app.utils.Utility;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${spring.data.rest.basePath}/auth/login")
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final Utility utility;

	public AuthenticationController(AuthenticationManager authenticationManager,
									TokenService tokenService, Utility utility) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		this.utility = utility;
	}

	@Operation(summary = "Authenticate user")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody @Valid AuthenticateUserInput payload) {
		String password = utility.decodeString(payload.getPassword());
		Authentication authToken = new UsernamePasswordAuthenticationToken(payload.getEmail(), password);
		var authenticatedUser = authenticationManager.authenticate(authToken);
		var jwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
		return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
	}
}