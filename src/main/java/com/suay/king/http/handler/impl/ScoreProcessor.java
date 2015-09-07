package com.suay.king.http.handler.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class ScoreProcessor extends AbstractRequestProcessor {

	@SuppressWarnings("unchecked")
	@Override
	public void processRequest(HttpExchange httpExchange) throws IOException {
		Map<Integer, String> pathParams = (Map<Integer, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PATH);
		Map<String, String> parameters = (Map<String, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PARAMS);
		String httpBody = "";
		Integer httpCode = HttpURLConnection.HTTP_OK;
		try {
			int id = Integer.parseInt(pathParams.get(1));
			String sessionKey = parameters.get(Constants.SESSION_KEY_PARAMETER);
			Integer score = Integer.parseInt((String) httpExchange.getAttribute(Constants.HTTP_ATT_BODY));
			gameManager.addScore(sessionKey, id, score);
		} catch (NumberFormatException e) {
			httpCode = HttpURLConnection.HTTP_BAD_REQUEST;
			httpBody = e.getMessage();
		} catch (SessionExpiredException e) {
			httpCode = HttpURLConnection.HTTP_UNAUTHORIZED;
			httpBody = e.getMessage();
		}
		writeResponse(httpExchange, httpBody, httpCode);

	}

}
