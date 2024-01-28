package com.forum.app.exception;

public class OwnRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OwnRuntimeException() {
		super();
	}

	public OwnRuntimeException(String message) {
		super(message);
	}
}