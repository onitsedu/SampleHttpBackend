package com.suay.king.exception;

public class LevelNotFoundException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer LVL_NOT_FOUND_ERROR_CODE = -1;

	public LevelNotFoundException() {
		super(LVL_NOT_FOUND_ERROR_CODE);
	}
}
