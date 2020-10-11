package com.diego.finances.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String message) {
		super(message);
	}
}
