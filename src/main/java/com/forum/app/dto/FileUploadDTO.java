package com.forum.app.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadDTO {
	private MultipartFile file;

	@NotBlank
	private String documentType;
}