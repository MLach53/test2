package com.spr.systemplacereservation.exceptions;

public class SeatNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1262116739868686206L;

	public SeatNotAvailableException(String message) {
		super(message);
	}
	
}
