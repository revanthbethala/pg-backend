package com.pg.exception;

public class DataAlreadyExistsException extends RuntimeException {

	public DataAlreadyExistsException(String message) {
		super(message);
	}
	public DataAlreadyExistsException() {
		super("Data already exists");
	}
}
