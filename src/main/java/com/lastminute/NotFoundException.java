package com.lastminute;

/**
 * Class that manage the exception generated by the searching flight service
 * @author Tiziano
 *
 */
public class NotFoundException extends LastMinuteException {

	private static final long serialVersionUID = -1000627788367067059L;

	/**
	 * Error message which states that a particular route has not been found
	 * @param message
	 */
	public NotFoundException(String message) {
		super(message);
	}

}