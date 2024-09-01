package com.forum.app.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.DocumentResponseDTO;
import com.forum.app.dto.FileDTO;
import com.forum.app.dto.FileUploadDTO;
import com.forum.app.entity.User;
import com.forum.app.service.StorageService;
import com.forum.app.utils.Utility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/files")
@SecurityRequirement(name = "bearer-key")
public class FileUploadController {

	private final StorageService storageService;
	private final String basePath;
	private final Utility utility;

	public FileUploadController(StorageService storageService, @Value("${spring.data.rest.basePath}") String basePath,
			Utility utility) {
		this.storageService = storageService;
		this.basePath = basePath;
		this.utility = utility;
	}

	@Operation(summary = "Save a file")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<DocumentResponseDTO> saveFile(@ModelAttribute @Valid FileUploadDTO payload,
			UriComponentsBuilder uriComponentsBuilder) {
		if (payload.getFile() == null) {
			throw new NullPointerException(utility.getMessage("forum.message.error.file", null));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		DocumentResponseDTO document = storageService.save(payload, user.getId());
		URI uri = uriComponentsBuilder.path(basePath + "/v1/files/{filename}")
				.buildAndExpand(document.getDocumentName()).toUri();
		return ResponseEntity.created(uri).body(document);
	}

	@Operation(summary = "Get a file by name")
	@GetMapping("/{userCode}/{fileName:.+}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource> getFile(@Parameter(description = "User code") @PathVariable String userCode,
			@Parameter(description = "Name of the file to search") @PathVariable String fileName) {
		Resource file = storageService.loadFile(userCode, fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@Operation(summary = "Get list of saved files")
	@GetMapping("/{userCode}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<FileDTO>> listUploadedFiles(
			@Parameter(description = "User code") @PathVariable String userCode, Model model) {
		List<FileDTO> fileDTOs = storageService.loadAllFiles(userCode).map(path -> {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setFileName(path.getFileName().toString());
			fileDTO.setPath(MvcUriComponentsBuilder
					.fromMethodName(FileUploadController.class, "getFile", userCode, path.getFileName().toString())
					.build().toUri().toString().replace("/$%7Bspring.data.rest.basePath%7D", basePath));
			return fileDTO;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(fileDTOs);
	}

	@Operation(summary = "Delete a file")
	@DeleteMapping("/{userCode}/{fileName}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteFile(@Parameter(description = "User code") @PathVariable String userCode,
			@Parameter(description = "Name of the file to be deleted") @PathVariable String fileName) {
		storageService.deleteFile(userCode, fileName);
		return ResponseEntity.noContent().build();
	}
}