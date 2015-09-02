package com.suay.king.exception;

/**
 * Checked exception to throw from the business layer
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Integer BASE_ERROR_CODE = -1;

	private Integer code;

	public BusinessException() {
		super();
		this.code = BASE_ERROR_CODE;
	}

	public BusinessException(Integer code) {
		super();
		this.code = code;
	}

	public BusinessException(String message, Integer code) {
		super(message);
		this.code = code;
	}

}