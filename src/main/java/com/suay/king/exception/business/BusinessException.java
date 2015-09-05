package com.suay.king.exception.business;

import com.suay.king.exception.BaseException;

/**
 * Checked exception to throw from the business layer
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static final Integer BASE_ERROR_CODE = -1;

	private Integer code;

	public BusinessException() {
		super();
		this.code = BASE_ERROR_CODE;
	}

	public BusinessException(Integer code) {
		super();

	}

	public BusinessException(String message, Integer code) {
		super(message);
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

}