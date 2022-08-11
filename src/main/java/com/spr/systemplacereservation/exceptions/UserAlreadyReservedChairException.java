package com.spr.systemplacereservation.exceptions;

public class UserAlreadyReservedChairException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6022077209183776554L;

	public UserAlreadyReservedChairException(String message) {
		super(message);
	}
}
