package com.suay.king.exception.business;

public class SessionExpiredException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer SESSION_EXPIRED_ERROR_CODE = -3;
	private static final String SESSION_EXPIRED_MESSAGE = "session expired";

	public SessionExpiredException() {
		super(SESSION_EXPIRED_MESSAGE, SESSION_EXPIRED_ERROR_CODE);
	}
}
