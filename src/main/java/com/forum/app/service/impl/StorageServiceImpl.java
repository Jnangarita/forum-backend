package com.forum.app.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forum.app.config.StorageProperties;
import com.forum.app.dto.DocumentResponseDTO;
import com.forum.app.entity.Document;
import com.forum.app.entity.User;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.DocumentRepository;
import com.forum.app.service.StorageService;
import com.forum.app.service.UserService;
import com.forum.app.utils.Utility;

@Service
public class StorageServiceImpl implements StorageService {

	private final Path rootLocation;
	private final Utility utility;
	private final UserService userService;
	private final DocumentRepository documentRepository;

	public StorageServiceImpl(StorageProperties properties, Utility utility, UserService userService,
			DocumentRepository documentRepository) {
		this.rootLocation = Paths.get(properties.getLocation());
		this.utility = utility;
		this.userService = userService;
		this.documentRepository = documentRepository;
	}

	@Override
	public DocumentResponseDTO save(MultipartFile file, String documentType, Long idUser) {
		try {
			User user = userService.findUser(idUser);
			Path userDirectory = this.rootLocation.resolve(user.getCode()).normalize().toAbsolutePath();
			if (!Files.exists(userDirectory)) {
				Files.createDirectories(userDirectory);
			}
			Path destinationFile = userDirectory.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (Files.exists(destinationFile)) {
				throw new OwnRuntimeException(utility.getMessage("forum.message.warn.file.already.exists",
						new Object[] { file.getOriginalFilename() }));
			}
			try (InputStream inputStream = file.getInputStream()) {
				init();
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				Document document = documentRepository.save(setDocumentData(user.getCode(), documentType,
						file.getOriginalFilename(), destinationFile.toString()));
				return new DocumentResponseDTO(document);
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
	public Resource loadFile(String userCode, String fileName) {
		try {
			Path file = load(userCode, fileName);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new OwnRuntimeException(
						utility.getMessage("forum.message.error.read.file", null) + " " + fileName);
			}
		} catch (MalformedURLException e) {
			throw new OwnRuntimeException(
					utility.getMessage("forum.message.error.url.format.not.correct", null) + fileName + " " + e);
		}
	}

	@Override
	public Path load(String userCode, String filename) {
		return rootLocation.resolve(userCode).resolve(filename).normalize().toAbsolutePath();
	}

	@Override
	public Stream<Path> loadAllFiles(String userCode) {
		try {
			Path userDirectory = this.rootLocation.resolve(userCode).normalize().toAbsolutePath();
			return Files.walk(userDirectory, 1).filter(path -> !path.equals(userDirectory))
					.map(userDirectory::relativize);
		} catch (IOException e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.failed.read.stored.files", null) + e);
		}
	}

	@Override
	public void deleteFile(String userCode, String fileName) {
		try {
			Path filePath = load(userCode, fileName);
			Files.delete(filePath);
		} catch (IOException e) {
			throw new OwnRuntimeException(
					utility.getMessage("forum.message.error.file.not.found", null) + " " + fileName);
		}
	}

	@Override
	public Document setDocumentData(String code, String documentType, String documentName, String path) {
		LocalDateTime currentDate = LocalDateTime.now();
		Document document = new Document();
		document.setUserCode(code);
		document.setDocumentType(documentType);
		document.setDocumentName(documentName);
		document.setDocumentPath(path);
		document.setCreationDate(currentDate);
		document.setDeleted(false);
		return document;
	}
}