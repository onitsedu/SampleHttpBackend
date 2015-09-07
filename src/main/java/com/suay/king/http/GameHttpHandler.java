package com.suay.king.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.suay.king.exception.http.BadMethodException;
import com.suay.king.exception.http.HttpException;
import com.suay.king.exception.http.PageNotFoundException;
import com.suay.king.http.handler.RequestProcessor;
import com.suay.king.http.handler.impl.HighScoreProcessor;
import com.suay.king.http.handler.impl.LoginProcessor;
import com.suay.king.http.handler.impl.ScoreProcessor;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class GameHttpHandler implements HttpHandler {

	private static final String LOGIN_REQUEST = "login";
	private static final String SCORE_REQUEST = "score";
	private static final String HIGH_SCORE_LIST_REQUEST = "highscorelist";

	private static final String LOGIN_PATTERN = "/(\\d*)/login";
	private static final String SCORE_PATTERN = "/(\\d*)/score\\?sessionkey=(.*)";
	private static final String HIGH_SCORE_PATTERN = "/(\\d*)/highscorelist";

	Map<String, RequestProcessor> requestProcessors;

	public GameHttpHandler() {
		requestProcessors = new HashMap<>();
		requestProcessors.put(LOGIN_REQUEST, new LoginProcessor());
		requestProcessors.put(SCORE_REQUEST, new ScoreProcessor());
		requestProcessors.put(HIGH_SCORE_LIST_REQUEST, new HighScoreProcessor());
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

	protected void writeErrorResponse(HttpExchange httpExchange, HttpException e) throws IOException {
		httpExchange.getResponseHeaders().add(Constants.CONTENT_TYPE, Constants.CONTENT_TEXT);
		httpExchange.sendResponseHeaders(e.getHttpCode(), e.getHttpMessage().length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(e.getHttpMessage().getBytes());
		os.close();
	}

	private String matchPath(HttpExchange httpExchange) throws HttpException {
		String uri = httpExchange.getRequestURI().toString();
		String method = httpExchange.getRequestMethod();
		if (uri.matches(LOGIN_PATTERN)) {
			if (Constants.HTTP_GET.equalsIgnoreCase(method)) {
				return LOGIN_REQUEST;
			} else {
				throw new BadMethodException();
			}
		} else if (uri.matches(HIGH_SCORE_PATTERN)) {
			if (Constants.HTTP_GET.equalsIgnoreCase(method)) {
				return HIGH_SCORE_LIST_REQUEST;
			} else {
				throw new BadMethodException();
			}
		} else if (uri.matches(SCORE_PATTERN)) {
			if (Constants.HTTP_POST.equalsIgnoreCase(method)) {
				return SCORE_REQUEST;
			} else {
				throw new BadMethodException();
			}
		} else {
			throw new PageNotFoundException();
		}

	}
}
