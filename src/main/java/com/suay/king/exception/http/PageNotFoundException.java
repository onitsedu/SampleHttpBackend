package com.suay.king.exception.http;

import java.net.HttpURLConnection;

public class PageNotFoundException extends HttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NOT_FONUND = "Page not found";

	public PageNotFoundException() {
		super(HttpURLConnection.HTTP_NOT_FOUND, PAGE_NOT_FONUND);
	}

}
