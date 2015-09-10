package com.suay.king.exception.business;

public class SessionExpiredException extends BusinessException {

	/**
	 * Serial for this class version
	 */
	private static final long serialVersionUID = -3870206690913776593L;

	private static final Integer SESSION_EXPIRED_ERROR_CODE = -3;
	private static final String SESSION_EXPIRED_MESSAGE = "session expired";

	public SessionExpiredException() {
		super(SESSION_EXPIRED_MESSAGE, SESSION_EXPIRED_ERROR_CODE);
	}
}
