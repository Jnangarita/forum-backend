package com.forum.app.enumeration;

public enum QuestionStatus {
	RESOLVED('R'),
	UNRESOLVED('U');

	private char status;

	QuestionStatus(char status) {
		this.status = status;
	}

	public char getStatus() {
		return this.status;
	}
}