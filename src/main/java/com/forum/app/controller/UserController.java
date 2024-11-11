package com.forum.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.BasicUserInfoDTO;
import com.forum.app.dto.request.ChangePasswordInput;
import com.forum.app.dto.MessageDTO;
import com.forum.app.dto.request.ResetPasswordInput;
import com.forum.app.dto.request.UpdateUserInput;
import com.forum.app.dto.request.SaveUserInput;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.dto.response.UserInfoDTO;
import com.forum.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/users")
public class UserController {

	private final UserService userService;
	private final String basePath;

	public UserController(UserService userService, @Value("${spring.data.rest.basePath}") String basePath) {
		this.userService = userService;
		this.basePath = basePath;
	}

	@Operation(summary = "Save a user")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserInfoDTO> createUser(@RequestBody @Valid SaveUserInput payload,
			UriComponentsBuilder uriComponentsBuilder) {
		UserInfoDTO user = userService.createUser(payload);
		URI url = uriComponentsBuilder.path(basePath + "/v1/users/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(url).body(user);
	}

	@Operation(summary = "Get a user by id")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<UserResponseDTO> getUserById(
			@Parameter(description = "Id of the user to search") @PathVariable Long id) {
		UserResponseDTO user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@Operation(summary = "Update a user")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<UserInfoDTO> updateUser(
			@Parameter(description = "Id of the user to search") @PathVariable Long id,
			@RequestBody @Valid UpdateUserInput payload) {
		UserInfoDTO user = userService.updateUser(id, payload);
		return ResponseEntity.ok(user);
	}

	@Operation(summary = "Get user list")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<List<BasicUserInfoDTO>> getUserList() {
		List<BasicUserInfoDTO> userList = userService.getUserList();
		return ResponseEntity.ok(userList);
	}

	@Operation(summary = "Delete a user")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<Void> deleteUser(@Parameter(description = "Id of the user to search") @PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Change user password")
	@PutMapping("/passwords/{id}")
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<MessageDTO> changePassword(
			@Parameter(description = "Id of the user to search") @PathVariable Long id,
			@RequestBody @Valid ChangePasswordInput payload) {
		MessageDTO message = userService.changePassword(id, payload);
		return ResponseEntity.ok(message);
	}

	@Operation(summary = "Reset user password")
	@PostMapping("/reset-password")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageDTO> resetPassword(@RequestBody @Valid ResetPasswordInput payload) {
		MessageDTO message = userService.resetPassword(payload);
		return ResponseEntity.ok(message);
	}
}