package com.suay.king.http.processor.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Optional;

import com.suay.king.business.GameManager;
import com.suay.king.business.impl.GameManagerImpl;
import com.suay.king.http.processor.RequestProcessor;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * @author csuay
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractRequestProcessor implements RequestProcessor {

    protected GameManager gameManager = GameManagerImpl.getInstance();

    private void writeResponse(HttpExchange httpExchange, String httpBody, Integer httpCode) throws IOException {
	httpExchange.getResponseHeaders().add(Constants.CONTENT_TYPE, Constants.CONTENT_TEXT);
	httpExchange.sendResponseHeaders(httpCode, httpBody.length());
	OutputStream os = httpExchange.getResponseBody();
	os.write(httpBody.getBytes());
	os.close();
    }

    protected void returnBadRequest(HttpExchange httpExchange, Optional<String> body) throws IOException {
	writeResponse(httpExchange, body.orElse(""), HttpURLConnection.HTTP_BAD_REQUEST);
    }

    protected void returnNotFound(HttpExchange httpExchange, Optional<String> body) throws IOException {
	writeResponse(httpExchange, body.orElse(""), HttpURLConnection.HTTP_NOT_FOUND);
    }

    protected void returnUnauthorized(HttpExchange httpExchange, Optional<String> body) throws IOException {
	writeResponse(httpExchange, body.orElse(""), HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    protected void returnOkResponse(HttpExchange httpExchange, Optional<String> body) throws IOException {
	writeResponse(httpExchange, body.orElse(""), HttpURLConnection.HTTP_OK);
    }
}