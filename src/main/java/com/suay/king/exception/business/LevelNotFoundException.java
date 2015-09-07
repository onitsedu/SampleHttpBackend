package com.suay.king.exception.business;

public class LevelNotFoundException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer LVL_NOT_FOUND_ERROR_CODE = -2;
	private static final String LVL_NOT_FOUND_MESSAGE = "session expired";

	public LevelNotFoundException() {
		super(LVL_NOT_FOUND_MESSAGE, LVL_NOT_FOUND_ERROR_CODE);
	}
}
