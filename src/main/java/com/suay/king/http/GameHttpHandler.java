package com.suay.king.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import com.suay.king.business.GameManager;
import com.suay.king.business.impl.GameManagerImpl;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.exception.http.BadMethodException;
import com.suay.king.exception.http.HttpException;
import com.suay.king.exception.http.PageNotFoundException;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class GameHttpHandler implements HttpHandler {

	/*
	 * Request for the different services
	 */
	private static final String LOGIN_REQUEST = "login";
	private static final String SCORE_REQUEST = "score";
	private static final String HIGH_SCORE_LIST_REQUEST = "highscorelist";

	private static final String LOGIN_PATTERN = "/(\\d*)/login";
	private static final String SCORE_PATTERN = "/(\\d*)/score\\?sessionkey=(.*)";
	private static final String HIGH_SCORE_PATTERN = "/(\\d*)/highscorelist";

	private GameManager gameManager;

	public GameHttpHandler() {
		this.gameManager = GameManagerImpl.getInstance();
	}

	@SuppressWarnings("unchecked")
	public void handle(HttpExchange httpExchange) throws IOException {
		String httpBody = "";
		int httpCode = HttpURLConnection.HTTP_OK;
		Map<String, String> parameters = (Map<String, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PARAMS);
		Map<Integer, String> pathParams = (Map<Integer, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PATH);

		try {
			int id = Integer.parseInt(pathParams.get(1));
			String request = matchPath(httpExchange);
			switch (request) {
			case LOGIN_REQUEST:
				httpBody = gameManager.login(id);
				break;
			case SCORE_REQUEST:
				final String sessionKey = parameters.get(Constants.SESSION_KEY_PARAMETER);
				final int score = Integer.parseInt((String) httpExchange.getAttribute(Constants.HTTP_ATT_BODY));
				gameManager.addScore(sessionKey, id, score);
				break;
			case HIGH_SCORE_LIST_REQUEST:
				httpBody = gameManager.listLevelRanking(id).toString();
				break;
			default:
			}
		} catch (NumberFormatException ex) {
			httpBody = "Invalid number format.";
			httpCode = HttpURLConnection.HTTP_BAD_REQUEST;
		} catch (SessionExpiredException e) {
			httpBody = "User unauthorized";
			httpCode = HttpURLConnection.HTTP_UNAUTHORIZED;
		} catch (LevelNotFoundException e) {
			httpBody = "level not found";
			httpCode = HttpURLConnection.HTTP_NOT_FOUND;
		} catch (HttpException e) {
			httpBody = e.getHttpMessage();
			httpCode = e.getHttpCode();
		}
		writeResponse(httpExchange, httpBody, httpCode);
	}

	private void writeResponse(HttpExchange httpExchange, String httpBody, Integer httpCode) throws IOException {

		httpExchange.getResponseHeaders().add(Constants.CONTENT_TYPE, Constants.CONTENT_TEXT);
		httpExchange.sendResponseHeaders(httpCode, httpBody.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(httpBody.getBytes());
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
