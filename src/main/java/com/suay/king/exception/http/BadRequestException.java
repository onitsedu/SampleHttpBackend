package com.suay.king.exception.http;

import java.net.HttpURLConnection;

public class BadRequestException extends HttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String BAD_REQUEST = "Bad request";

	public BadRequestException() {
		super(HttpURLConnection.HTTP_BAD_REQUEST, BAD_REQUEST);
	}

}
