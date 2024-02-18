package com.forum.app.enumeration;

public enum AnswerStatus {
	SENT('S'),
	RIGHT('R'),
	WRONG('W');

	private char status;

	AnswerStatus(char status) {
		this.status = status;
	}

	public char getStatus() {
		return this.status;
	}
}