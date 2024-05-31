package com.forum.app.enumeration;

public enum GeneralEnum {
	GENERAL_ERROR_MESSAGE("forum.message.error.general");

	private final String messageKey;

	GeneralEnum(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}