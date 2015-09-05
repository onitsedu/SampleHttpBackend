package com.suay.king.exception.http;

public class HttpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int httpCode;
	private String httpMessage;

	public HttpException(int code, String httpMessage) {
		super();
		this.httpCode = code;
		this.httpMessage = httpMessage;
	}

	/**
	 * @return the httpCode
	 */
	public int getHttpCode() {
		return httpCode;
	}

	/**
	 * @return the httpMessage
	 */
	public String getHttpMessage() {
		return httpMessage;
	}

}
