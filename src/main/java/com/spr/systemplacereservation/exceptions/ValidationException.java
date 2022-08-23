package com.spr.systemplacereservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 57892875l;

	private HttpStatus httpStatus;

	public ValidationException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ResponseEntity<Object> getResponseEntity() {
		return new ResponseEntity<>(super.getMessage(), httpStatus);
	}

}
