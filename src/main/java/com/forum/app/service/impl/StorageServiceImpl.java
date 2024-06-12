package com.forum.app.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

	@Override
	public Resource loadFile(String fileName) {
		try {
			Path file = load(fileName);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new OwnRuntimeException("Could not read file: " + fileName);
			}
		} catch (MalformedURLException e) {
			throw new OwnRuntimeException("Could not read file: " + fileName + " " + e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Stream<Path> loadAllFiles() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new OwnRuntimeException("Failed to read stored files " + e);
		}
	}

	@Override
	public void deleteFile(String fileName) {
		try {
			Path filePath = load(fileName);
			Files.delete(filePath);
		} catch (IOException e) {
			throw new OwnRuntimeException("File not found: " + fileName);
		}
	}
}