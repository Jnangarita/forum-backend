package com.forum.app.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.service.StorageService;
import com.forum.app.utils.Utility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/files")
@SecurityRequirement(name = "bearer-key")
public class FileUploadController {

	private final StorageService storageService;
	private final Utility utility;

	public FileUploadController(StorageService storageService, Utility utility) {
		this.storageService = storageService;
		this.utility = utility;
	}

	@Operation(summary = "Save a file")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file,
			UriComponentsBuilder uriComponentsBuilder) {
		storageService.save(file);
		String fileName = file.getOriginalFilename();
		URI uri = uriComponentsBuilder.path("/v1/files/{filename}").buildAndExpand(fileName).toUri();
		return ResponseEntity.created(uri)
				.body(utility.getMessage("forum.message.info.file.uploaded.successfully", new Object[] { fileName }));
	}
}