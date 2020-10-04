package com.diego.finances.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(Long id) {
		super("Object not found!" + " Id: " + id);
	}
}