package com.revature.exceptions;
/**
 * Class that handles any bad request exception 
 * thrown in the UserController
 * @author 1811-Java-Nick 12/27/18
 *
 */
public class BadRequestException extends RuntimeException {

	/**
	 * No arg constructor for the BadRequestException class
	 */
	public BadRequestException() {
		super();
	}

	/**
	 * Overload constructor for the BadRequestException class
	 * @param arg0 the message itself
	 * @param arg1 the cause of the exception
	 * @param arg2 enable suppression
	 * @param arg3 writableStackTrace
	 */
	public BadRequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Overload constructor for the BadRequestException class
	 * @param arg0 the message itself
	 * @param arg1 the cause of th exception
	 */
	public BadRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Overload constructor for the BadRequestException class
	 * @param arg0 the message
	 */
	public BadRequestException(String arg0) {
		super(arg0);
	}

	/**
	 * Overload constructor for the BadRequestException class
	 * @param arg0 the cause of the exception
	 */
	public BadRequestException(Throwable arg0) {
		super(arg0);
	}

}
