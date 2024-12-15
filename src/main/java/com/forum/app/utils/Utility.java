package com.forum.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.app.dto.request.IdValueInput;
import com.forum.app.exception.OwnRuntimeException;
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

	private static final String JSON_FORMAT_ERROR_MSG = "forum.message.error.casting.string.to.json.format";
	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	private final MessageSource messageSource;
	private final ObjectMapper objectMapper;
	private final PasswordEncoder passwordEncoder;
	private static final SecureRandom randomNum = new SecureRandom();

	public Utility(MessageSource messageSource, ObjectMapper objectMapper,
				   PasswordEncoder passwordEncoder) {
		this.messageSource = messageSource;
		this.objectMapper = objectMapper;
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

	public List<IdValueInput> convertJsonToIdValueDTOList(String jsonString) {
		try {
			return objectMapper.readValue(jsonString, new TypeReference<List<IdValueInput>>() {
			});
		} catch (JsonProcessingException e) {
			throw new OwnRuntimeException(getMessage(JSON_FORMAT_ERROR_MSG, null));
		}
	}

	public IdValueInput convertJsonToIdValueDTO(String jsonString) {
		try {
			return objectMapper.readValue(jsonString, new TypeReference<IdValueInput>() {
			});
		} catch (JsonProcessingException e) {
			throw new OwnRuntimeException(getMessage(JSON_FORMAT_ERROR_MSG, null));
		}
	}

	public Long convertToLongType(Object column) {
		return column != null ? ((Number) column).longValue() : null;
	}

	public Integer convertToIntType(Object column) {
		return column != null ? ((Number) column).intValue() : null;
	}

	public String convertToStringType(Object column) {
		return column != null ? column.toString() : null;
	}

	public String encryptPassword(String password){
		return passwordEncoder.encode(password);
	}

	public String decodeString(String encryptedString, String base64Key) {
		try {
			byte[] decodedKey = Base64.getDecoder().decode(base64Key);
			SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
			return new String(decryptedBytes);
		} catch (Exception e) {
			throw new OwnRuntimeException(getMessage("forum.message.error.decrypting.string", new Object[] { encryptedString }));
		}
	}
}