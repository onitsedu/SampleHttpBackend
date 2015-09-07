package com.suay.king.http.handler.impl;

import java.io.IOException;
import java.io.OutputStream;

import com.suay.king.business.GameManager;
import com.suay.king.business.impl.GameManagerImpl;
import com.suay.king.http.handler.RequestProcessor;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public abstract class AbstractRequestProcessor implements RequestProcessor {

	protected GameManager gameManager = GameManagerImpl.getInstance();

	protected void writeResponse(HttpExchange httpExchange, String httpBody, Integer httpCode) throws IOException {
		httpExchange.getResponseHeaders().add(Constants.CONTENT_TYPE, Constants.CONTENT_TEXT);
		httpExchange.sendResponseHeaders(httpCode, httpBody.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(httpBody.getBytes());
		os.close();
	}

}
