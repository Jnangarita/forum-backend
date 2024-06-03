package com.forum.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.UserDTO;
import com.forum.app.dto.UserResponseDTO;
import com.forum.app.service.UserService;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Value("${spring.data.rest.basePath}")
	private String basePath;

	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		UserResponseDTO user = userService.createUser(payload);
		URI url = uriComponentsBuilder.path(basePath + "/v1/users/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(url).body(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		UserResponseDTO user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO payload) {
		UserResponseDTO user = userService.updateUser(id, payload);
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getUserList() {
		List<UserResponseDTO> userList = userService.getUserList();
		return ResponseEntity.ok(userList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}