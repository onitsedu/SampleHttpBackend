/*
 *   BackEndHttpHandler.java
 * 
 * Copyright(c) 2014 Christian Delgado. All Rights Reserved.
 * This software is the proprietary information of Christian Delgado
 * 
 */
package com.suay.king.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import com.suay.king.business.LevelManager;
import com.suay.king.business.SessionManager;
import com.suay.king.business.singleton.ManagersSingleton;
import com.suay.king.exception.BusinessException;
import com.suay.king.model.GameUser;
import com.suay.king.model.UserScore;
import com.suay.king.model.UserSession;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * HttpHandler for to deploy the Http Rest Web Services, for the BackEnd
 * MiniGame Server.
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/28/14
 */
@SuppressWarnings("restriction")
public class BackEndHttpHandler implements HttpHandler {

	/*
	 * Value for the Parameters previously treated in the BackEndHttpFilter
	 */
	public static final String PARAMETER_ATTRIBUTE = "parameters";
	public static final String REQUEST_PARAMETER = "request";
	public static final String LEVEL_ID_PARAMETER = "levelid";
	public static final String SESSION_KEY_PARAMETER = "sessionkey";
	public static final String SCORE_PARAMETER = "score";
	public static final String USER_ID_PARAMETER = "userid";
	/*
	 * Request for the different services
	 */
	public static final String LOGIN_REQUEST = "login";
	public static final String SCORE_REQUEST = "score";
	public static final String HIGH_SCORE_LIST_REQUEST = "highscorelist";
	/*
	 * Http Content type constants
	 */
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_TEXT = "text/plain";

	private LevelManager lvlManager;
	private SessionManager sessionManager;

	/**
	 * Creates a new instance of BackEndHttpHandler
	 *
	 * @param gameManager
	 */
	public BackEndHttpHandler() {
		this.lvlManager = ManagersSingleton.INSTANCE.getLevelManager();
		sessionManager = ManagersSingleton.INSTANCE.getSessionManager();
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		String httpBody = "";
		int httpCode = HttpURLConnection.HTTP_OK;
		Map<String, String> parameters = (Map<String, String>) httpExchange.getAttribute(PARAMETER_ATTRIBUTE);
		String request = parameters.get(REQUEST_PARAMETER);
		try {
			switch (request) {
			case LOGIN_REQUEST:
				final int userId = Integer.parseInt(parameters.get(USER_ID_PARAMETER));
				GameUser user = new GameUser();
				user.setUserId(userId);
				UserSession session = sessionManager.addSession(user);
				httpBody = session.getSessionId();

				break;
			case SCORE_REQUEST:
				final String sessionKey = parameters.get(SESSION_KEY_PARAMETER);
				final int score = Integer.parseInt(parameters.get(SCORE_PARAMETER));
				final int levelId = Integer.parseInt(parameters.get(LEVEL_ID_PARAMETER));

				lvlManager.addUserScore(sessionKey, levelId, score);
				break;
			case HIGH_SCORE_LIST_REQUEST:
				final int levelId1 = Integer.parseInt(parameters.get(LEVEL_ID_PARAMETER));
				lvlManager.getLevelRanking(levelId1);
				break;
			default:
			}
		} catch (NumberFormatException ex) {
			httpBody = "Invalid number format.";
			httpCode = HttpURLConnection.HTTP_BAD_REQUEST;
			httpExchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TEXT);
			httpExchange.sendResponseHeaders(httpCode, httpBody.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(httpBody.getBytes());
			os.close();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
