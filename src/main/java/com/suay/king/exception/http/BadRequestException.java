package com.suay.king.exception.http;

import java.net.HttpURLConnection;

public class BadRequestException extends HttpException {

	/**
	 * Serial for this class version
	 */
	private static final long serialVersionUID = 7931726140957213542L;
	private static final String BAD_REQUEST = "Bad request";

	public BadRequestException() {
		super(HttpURLConnection.HTTP_BAD_REQUEST, BAD_REQUEST);
	}

}
