package com.suay.king.http.processor.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;

import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class LoginProcessor extends AbstractRequestProcessor {

	@SuppressWarnings("unchecked")
	public void processRequest(HttpExchange httpExchange) throws IOException {
		Map<Integer, String> pathParams = (Map<Integer, String>) httpExchange
				.getAttribute(Constants.HTTP_ATT_PATH);
		try {
			int id = Integer.parseInt(pathParams.get(1));
			returnOkResponse(httpExchange,
					Optional.ofNullable(gameManager.login(id)));
		} catch (NumberFormatException e) {
			returnBadRequest(httpExchange, Optional.ofNullable(e.getMessage()));

		}
	}
}
