package com.forum.app.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.forum.app.dto.DocumentResponseDTO;
import com.forum.app.entity.Document;

public interface StorageService {

	void init();

	DocumentResponseDTO save(MultipartFile file, String documentType, Long idUser);

	Resource loadFile(String fileName);

	Path load(String filename);

	Stream<Path> loadAllFiles();

	void deleteFile(String fileName);

	Document setDocumentData(String code, String documentType, String documentName, String path);
}