package com.forum.app.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;

import com.forum.app.dto.DocumentResponseDTO;
import com.forum.app.dto.FileUploadDTO;
import com.forum.app.entity.Document;

public interface StorageService {

	void init();

	DocumentResponseDTO save(FileUploadDTO payload, Long idUser);

	Resource loadFile(String fileName, String fileName2);

	Path load(String userCode, String filename);

	Stream<Path> loadAllFiles(String userCode);

	void deleteFile(String userCode, String fileName);

	Document setDocumentData(String code, String documentType, String documentName, String path);
}