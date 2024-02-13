package com.forum.app.enumeration;

public enum QuestionStatus {
	ANSWERED('A'),
	UNANSWERED('U');

	private char status;

	QuestionStatus(char status) {
		this.status = status;
	}

	public char getStatus() {
		return this.status;
	}
}