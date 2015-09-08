package com.suay.king.http.handler.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HighScoreProcessor extends AbstractRequestProcessor {

	@SuppressWarnings("unchecked")
	@Override
	public void processRequest(HttpExchange httpExchange) throws IOException {
		Map<Integer, String> pathParams = (Map<Integer, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PATH);

		String httpBody = "";
		Integer httpCode = HttpURLConnection.HTTP_OK;
		try {
			int id = Integer.parseInt(pathParams.get(1));
			httpBody = gameManager.listLevelRanking(id).toString();
		} catch (LevelNotFoundException e) {
			httpCode = HttpURLConnection.HTTP_NOT_FOUND;
			httpBody = e.getMessage();
		} catch (NumberFormatException e) {
			httpCode = HttpURLConnection.HTTP_BAD_REQUEST;
			httpBody = e.getMessage();
		}

		writeResponse(httpExchange, httpBody, httpCode);

	}

}
