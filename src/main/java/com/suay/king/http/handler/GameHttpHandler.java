package com.suay.king.http.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.suay.king.exception.http.BadMethodException;
import com.suay.king.exception.http.HttpException;
import com.suay.king.exception.http.PageNotFoundException;
import com.suay.king.http.processor.RequestProcessor;
import com.suay.king.http.processor.impl.HighScoreProcessor;
import com.suay.king.http.processor.impl.LoginProcessor;
import com.suay.king.http.processor.impl.ScoreProcessor;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 
 * @author csuay
 *
 */
@SuppressWarnings("restriction")
public class GameHttpHandler implements HttpHandler {

	Map<String, RequestProcessor> requestProcessors;

	public GameHttpHandler() {
		requestProcessors = new HashMap<String, RequestProcessor>();
		requestProcessors.put(Constants.LOGIN_REQUEST, new LoginProcessor());
		requestProcessors.put(Constants.SCORE_REQUEST, new ScoreProcessor());
		requestProcessors.put(Constants.HIGH_SCORE_LIST_REQUEST,
				new HighScoreProcessor());
	}

	public void handle(HttpExchange httpExchange) throws IOException {

		String request;
		try {
			request = matchPath(httpExchange);
			RequestProcessor requestProcessor = requestProcessors.get(request);
			requestProcessor.processRequest(httpExchange);
		} catch (HttpException e) {
			writeErrorResponse(httpExchange, e);
		}

	}

	protected void writeErrorResponse(HttpExchange httpExchange, HttpException e)
			throws IOException {
		httpExchange.getResponseHeaders().add(Constants.CONTENT_TYPE,
				Constants.CONTENT_TEXT);
		httpExchange.sendResponseHeaders(e.getHttpCode(), e.getHttpMessage()
				.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(e.getHttpMessage().getBytes());
		os.close();
	}

	private String matchPath(HttpExchange httpExchange) throws HttpException {
		String uri = httpExchange.getRequestURI().toString();
		String method = httpExchange.getRequestMethod();
		if (uri.matches(Constants.LOGIN_PATTERN)) {
			if (Constants.HTTP_GET.equalsIgnoreCase(method)) {
				return Constants.LOGIN_REQUEST;
			} else {
				throw new BadMethodException();
			}
		} else if (uri.matches(Constants.HIGH_SCORE_PATTERN)) {
			if (Constants.HTTP_GET.equalsIgnoreCase(method)) {
				return Constants.HIGH_SCORE_LIST_REQUEST;
			} else {
				throw new BadMethodException();
			}
		} else if (uri.matches(Constants.SCORE_PATTERN)) {
			if (Constants.HTTP_POST.equalsIgnoreCase(method)) {
				return Constants.SCORE_REQUEST;
			} else {
				throw new BadMethodException();
			}
		} else {
			throw new PageNotFoundException();
		}

	}
}
