package com.forum.app.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forum.app.config.StorageProperties;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.service.StorageService;
import com.forum.app.utils.Utility;

@Service
public class StorageServiceImpl implements StorageService {

	private final Path rootLocation;
	private final Utility utility;

	public StorageServiceImpl(StorageProperties properties, Utility utility) {
		this.rootLocation = Paths.get(properties.getLocation());
		this.utility = utility;
	}

	@Override
	public void save(MultipartFile file) {
		try {
			Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			try (InputStream inputStream = file.getInputStream()) {
				init();
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.storing.file", null) + e);
		}
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.storage.folder", null) + e);
		}
	}
}