package com.forum.app.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.forum.app.dto.request.IdValueInput;
import com.forum.app.exception.OwnRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Utility {
	private final String secretKey;

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	private final MessageSource messageSource;

	private final PasswordEncoder passwordEncoder;
	private static final SecureRandom randomNum = new SecureRandom();

	public Utility(@Value("${secret.key}") String secretKey, MessageSource messageSource,
				   PasswordEncoder passwordEncoder) {
		this.secretKey = secretKey;
		this.messageSource = messageSource;
		this.passwordEncoder = passwordEncoder;
	}

	public String getCustomErrorMessage(DataIntegrityViolationException e) {
		String description = e.getCause() != null ? e.getCause().getCause().getLocalizedMessage() : e.getMessage();
		if (description != null) {
			Pattern pattern = Pattern.compile("FOREIGN KEY \\(`(.*?)`\\) REFERENCES `(.*?)`");
			Matcher matcher = pattern.matcher(description);
			if (matcher.find()) {
				String fieldName = matcher.group(1);
				String tableName = matcher.group(2);
				String message = getMessage("forum.message.error.foreign.key.constraint.description", null);
				description = String.format(message, fieldName, tableName);
			}
		}
		return description;
	}

	public String getMessage(String messageKey, Object[] arr) {
		return messageSource.getMessage(messageKey, arr, LocaleContextHolder.getLocale());
	}

	public String getFileExtension(String fileName) {
		if (fileName == null) {
			throw new NullPointerException(getMessage("forum.message.warn.file.not.empty", null));
		}
		return fileName.lastIndexOf('.') != -1 ? fileName.substring(fileName.lastIndexOf('.') + 1) : "";
	}

	public String generatePassword() {
		final int PASSWORD_LENGTH = 8;
		StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = randomNum.nextInt(characters.length());
			password.append(characters.charAt(index));
		}
		return password.toString();
	}

	public String getExceptionMsg(Exception e, String errorKey) {
		return e.getMessage() == null ? getMessage(errorKey, null) : e.getMessage();
	}

	public LocalDateTime getDate(Map<String, Object> result, String column) {
		return result.get(column) != null ? ((Timestamp) result.get(column)).toLocalDateTime() : null;
	}

	public Long convertToLongType(Object column) {
		return column != null ? ((Number) column).longValue() : null;
	}

	public Integer convertToIntType(Object column) {
		return column != null ? ((Number) column).intValue() : null;
	}

	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public String decodeString(String encryptedString) {
		try {
			byte[] decodedKey = Base64.getDecoder().decode(secretKey);
			SecretKeySpec key = new SecretKeySpec(decodedKey, "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
			return new String(decryptedBytes);
		} catch (Exception e) {
			throw new OwnRuntimeException(getMessage("forum.message.error.decrypting.string", new Object[]{encryptedString}));
		}
	}

	public LocalDateTime convertToLocalDateTime(Object value) {
		return value != null ? ((Timestamp) value).toLocalDateTime() : null;
	}

	public List<IdValueInput> convertJsonToIdValueInputList(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, new TypeReference<List<IdValueInput>>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting JSON to IdValueInput list", e);// TODO personalizar la excepción
		}
	}

	public String convertToJsonFormat(Object dto) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			return objectMapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			return "{\"error\":\"Failed to serialize payload\"}"; // TODO personalizar el mensaje
		}
	}
}