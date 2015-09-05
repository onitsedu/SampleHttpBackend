package com.suay.king.exception.http;

import java.net.HttpURLConnection;

public class BadMethodException extends HttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String BAD_METHOD = "Page not found";

	public BadMethodException() {
		super(HttpURLConnection.HTTP_BAD_METHOD, BAD_METHOD);
	}

}
