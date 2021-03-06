package com.arzio.arziolib.api.exception;

public class CDAReflectionException extends RuntimeException{

	private static final long serialVersionUID = 5762309374021920293L;
	
	public CDAReflectionException(String message) {
		super(message);
	}
	
	public CDAReflectionException(String message, Exception e) {
		super(message, e);
	}
	
	public CDAReflectionException(Exception e) {
		super(e);
	}
}
