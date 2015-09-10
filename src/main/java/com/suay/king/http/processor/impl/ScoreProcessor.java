package com.suay.king.http.processor.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * @author csuay
 *
 */
@SuppressWarnings("restriction")
public class ScoreProcessor extends AbstractRequestProcessor {

    @SuppressWarnings("unchecked")
    public void processRequest(HttpExchange httpExchange) throws IOException {
	Map<Integer, String> pathParams = (Map<Integer, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PATH);
	Map<String, String> parameters = (Map<String, String>) httpExchange.getAttribute(Constants.HTTP_ATT_PARAMS);
	try {
	    int id = Integer.parseInt(pathParams.get(1));
	    String sessionKey = parameters.get(Constants.SESSION_KEY_PARAMETER);
	    Integer score = Integer.parseInt((String) httpExchange.getAttribute(Constants.HTTP_ATT_BODY));
	    gameManager.addScore(sessionKey, id, score);
	    returnOkResponse(httpExchange, Optional.ofNullable(""));
	} catch (NumberFormatException e) {
	    returnBadRequest(httpExchange, Optional.ofNullable(e.getMessage()));
	} catch (SessionExpiredException e) {
	    returnUnauthorized(httpExchange, Optional.ofNullable(e.getMessage()));
	}

    }

}
