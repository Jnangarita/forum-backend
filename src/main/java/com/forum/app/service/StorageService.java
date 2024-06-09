package com.forum.app.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void save(MultipartFile file);

	Resource loadFile(String fileName);

	Path load(String filename);

	Stream<Path> loadAllFiles();
}