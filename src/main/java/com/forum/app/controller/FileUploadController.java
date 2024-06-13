package com.forum.app.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.app.dto.DocumentResponseDTO;
import com.forum.app.dto.FileDTO;
import com.forum.app.entity.User;
import com.forum.app.service.StorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/files")
@SecurityRequirement(name = "bearer-key")
public class FileUploadController {

	private final StorageService storageService;
	private final String basePath;

	public FileUploadController(StorageService storageService, @Value("${spring.data.rest.basePath}") String basePath) {
		this.storageService = storageService;
		this.basePath = basePath;
	}

	@Operation(summary = "Save a file")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<DocumentResponseDTO> saveFile(@RequestParam("file") MultipartFile file,
			@RequestParam("documentType") String documentType, UriComponentsBuilder uriComponentsBuilder) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		DocumentResponseDTO document = storageService.save(file, documentType, user.getId());
		URI uri = uriComponentsBuilder.path(basePath + "/v1/files/{filename}")
				.buildAndExpand(document.getDocumentName()).toUri();
		return ResponseEntity.created(uri).body(document);
	}

	@Operation(summary = "Get a file by name")
	@GetMapping("/{fileName:.+}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource> getFile(
			@Parameter(description = "Name of the file to search") @PathVariable String fileName) {
		Resource file = storageService.loadFile(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@Operation(summary = "Get list of saved files")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<FileDTO>> listUploadedFiles(Model model) {
		List<FileDTO> fileDTOs = storageService.loadAllFiles().map(path -> {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setFileName(path.getFileName().toString());
			fileDTO.setPath(MvcUriComponentsBuilder
					.fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build()
					.toUri().toString().replace("/$%7Bspring.data.rest.basePath%7D", basePath));
			return fileDTO;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(fileDTOs);
	}

	@Operation(summary = "Delete a file")
	@DeleteMapping("/{fileName}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteFile(
			@Parameter(description = "Name of the file to be deleted") @PathVariable String fileName) {
		storageService.deleteFile(fileName);
		return ResponseEntity.noContent().build();
	}
}