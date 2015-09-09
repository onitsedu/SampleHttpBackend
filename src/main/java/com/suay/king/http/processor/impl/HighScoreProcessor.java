package com.suay.king.http.processor.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HighScoreProcessor extends AbstractRequestProcessor {

	@SuppressWarnings("unchecked")
	public void processRequest(HttpExchange httpExchange) throws IOException {
		Map<Integer, String> pathParams = (Map<Integer, String>) httpExchange
				.getAttribute(Constants.HTTP_ATT_PATH);
		try {
			int id = Integer.parseInt(pathParams.get(1));
			returnOkResponse(httpExchange, Optional.ofNullable(gameManager
					.listLevelRanking(id).toString()));
		} catch (LevelNotFoundException e) {
			returnNotFound(httpExchange, Optional.ofNullable(e.getMessage()));
		} catch (NumberFormatException e) {
			returnBadRequest(httpExchange, Optional.ofNullable(e.getMessage()));
		}

	}
}
