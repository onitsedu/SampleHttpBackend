package com.suay.king.exception.http;

import java.net.HttpURLConnection;

public class PageNotFoundException extends HttpException {

	/**
	 * Serial for this class version
	 */
	private static final long serialVersionUID = 8288909583546530683L;

	private static final String PAGE_NOT_FONUND = "Page not found";

	public PageNotFoundException() {
		super(HttpURLConnection.HTTP_NOT_FOUND, PAGE_NOT_FONUND);
	}

}
