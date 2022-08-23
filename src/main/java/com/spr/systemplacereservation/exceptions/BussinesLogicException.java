package com.spr.systemplacereservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BussinesLogicException extends RuntimeException {

    /**
     * 
     */

    private HttpStatus httpStatus;
    private static final long serialVersionUID = 57892875l;

    public BussinesLogicException(String message, HttpStatus httpStatus) {
	super(message);
	this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
	return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
	this.httpStatus = httpStatus;
    }

    public ResponseEntity<Object> getEntity() {
	return new ResponseEntity<>(super.getMessage(), httpStatus);
    }

}
