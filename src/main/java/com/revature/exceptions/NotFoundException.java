package com.revature.exceptions;
/**
 * class that deals with NotFoundException
 * @author 1811-Java-Nick 12/27/18
 *
 */
public class NotFoundException extends RuntimeException {

	/**
	 * No args constructor for the NotFoundException class
	 */
	public NotFoundException() {
		super();
	}

	/**
	 * Overload constructor for the NotFoundException class
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Overload constructor for the NotFoundException class
	 * @param message
	 * @param cause
	 */
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Overload constructor for the NotFoundException class
	 * @param message
	 */
	public NotFoundException(String message) {
		super(message);
	}

	/**
	 * Overload constructor for the NotFoundException class
	 * @param cause
	 */
	public NotFoundException(Throwable cause) {
		super(cause);
	}

}
