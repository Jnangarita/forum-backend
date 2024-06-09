package com.forum.app.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
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

import com.forum.app.dto.FileDTO;
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
	private final String basePath;

	public FileUploadController(StorageService storageService, Utility utility,
			@Value("${spring.data.rest.basePath}") String basePath) {
		this.storageService = storageService;
		this.utility = utility;
		this.basePath = basePath;
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

	@GetMapping("/{fileName:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
		Resource file = storageService.loadFile(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@Operation(summary = "Get list of saved files")
	@GetMapping
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
}