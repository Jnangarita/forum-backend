package com.forum.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataIntegrityViolationException;

@Component
public class Utility {

	private final MessageSource messageSource;

	public Utility(MessageSource messageSource) {
		this.messageSource = messageSource;
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
}