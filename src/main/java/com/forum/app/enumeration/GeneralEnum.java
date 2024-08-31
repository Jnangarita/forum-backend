package com.forum.app.enumeration;

public enum GeneralEnum {
	GENERAL_ERROR_MESSAGE("forum.message.error.general"),
	NOT_SPECIFIED("UNK"),
	BLANK_IMG("src/assets/img/blank-profile.png");

	private final String messageKey;

	GeneralEnum(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}