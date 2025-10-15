package com.becoder.exception;

public class JwtAuthenticationExpiredException extends RuntimeException {

	public JwtAuthenticationExpiredException(String message) {
		super(message);
	}

}
