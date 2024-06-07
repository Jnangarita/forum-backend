package com.forum.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void save(MultipartFile file);
}