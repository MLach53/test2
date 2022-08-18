package com.spr.systemplacereservation.exceptions;

public class SeatNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1570327248902569951L;

	public SeatNotFoundException(String message) {
		super(message);
	}

}
