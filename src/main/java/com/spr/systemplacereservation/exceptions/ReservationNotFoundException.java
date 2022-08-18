package com.spr.systemplacereservation.exceptions;

public class ReservationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1570327248902569951L;

	public ReservationNotFoundException(String message) {
		super(message);
	}

}
