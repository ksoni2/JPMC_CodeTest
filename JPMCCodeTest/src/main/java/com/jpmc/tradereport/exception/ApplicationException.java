package com.jpmc.tradereport.exception;

/**
 * The Class ApplicationException.
 *
 * @author Kamal Soni
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 3205076645785514408L;


	/**
	 * Instantiates a new ApplicationException.
	 *
	 * @param message
	 *            the message
	 */
	public ApplicationException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new application exception.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public ApplicationException(final String message, final Exception exception) {
		super(message, exception);
	}

	/**
	 * Instantiates a new application exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
