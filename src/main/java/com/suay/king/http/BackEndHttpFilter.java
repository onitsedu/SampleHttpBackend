/*
 *   BackEndHttpFilter.java
 * 
 * Copyright(c) 2014 Christian Delgado. All Rights Reserved.
 * This software is the proprietary information of Christian Delgado
 * 
 */
package com.suay.king.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

/**
 * Class used to Filter all the HttpRequest in the BackEnd Server
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/28/14
 */
@SuppressWarnings("restriction")
public class BackEndHttpFilter extends Filter {

	/*
	 * URL Patterns for all the valid requests for each service
	 */
	private static final String LOGIN_PATTERN = "/(\\d*)/login";
	private static final String SCORE_PATTERN = "/(\\d*)/score\\?sessionkey=(.*)";
	private static final String HIGH_SCORE_LIST_PATTERN = "/(\\d*)/highscorelist";

	/**
	 * Method where all the filter are applied
	 *
	 * @param httpExchange
	 * @param chain
	 * @throws IOException
	 */
	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		try {
			Map<String, String> parameters;
			// validate the URL from the request
			String uri = httpExchange.getRequestURI().toString();
			if (uri.matches(LOGIN_PATTERN)) {
				parameters = parseLoginParameters(httpExchange);
			} else if (uri.matches(SCORE_PATTERN)) {
				parameters = parseScoreParameters(httpExchange);
			} else if (uri.matches(HIGH_SCORE_LIST_PATTERN)) {
				parameters = parseHighScoreListParameters(httpExchange);
			} else {
				// TODO throw exception
				parameters = new HashMap<String, String>();
				// is an invalid url
			}
			// Pass the parameter to the BackEndHttpHandler
			httpExchange.setAttribute(BackEndHttpHandler.PARAMETER_ATTRIBUTE, parameters);
			chain.doFilter(httpExchange);
		} catch (Exception ex) {

			exceptionHandledResponse(ex.getMessage(), httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
			ex.printStackTrace();
		}
	}

	/**
	 * Method where parse the Parameters from the Login Request
	 *
	 * @param httpExchange
	 * @return
	 * @throws NotValidHttpException
	 */
	private static Map<String, String> parseLoginParameters(HttpExchange httpExchange) {
		validHttpMethod(httpExchange, "GET");
		Map<String, String> parameters = new HashMap<String, String>();
		String uri = httpExchange.getRequestURI().toString();
		String userId = uri.split("/")[1];
		parameters.put(BackEndHttpHandler.REQUEST_PARAMETER, BackEndHttpHandler.LOGIN_REQUEST);
		parameters.put(BackEndHttpHandler.USER_ID_PARAMETER, userId);
		return parameters;
	}

	/**
	 * Method where parse the Parameters from the Score Request
	 *
	 * @param httpExchange
	 * @return
	 * @throws Exception
	 * @throws NotValidHttpException
	 */
	private static Map<String, String> parseScoreParameters(HttpExchange httpExchange) throws Exception {
		validHttpMethod(httpExchange, "POST");
		Map<String, String> parameters = new HashMap<String, String>();
		String uri = httpExchange.getRequestURI().toString();
		String[] uriStrings = uri.split("/");
		String levelId = uriStrings[1];
		String[] paramsStrings = uriStrings[2].split(BackEndHttpHandler.SESSION_KEY_PARAMETER + "=");
		String sessionKey = paramsStrings[1];
		String score;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			try {
				score = bufferedReader.readLine();
			} finally {
				bufferedReader.close();
				inputStreamReader.close();
			}
		} catch (Exception ex) {
			throw ex;
		}
		parameters.put(BackEndHttpHandler.REQUEST_PARAMETER, BackEndHttpHandler.SCORE_REQUEST);
		parameters.put(BackEndHttpHandler.LEVEL_ID_PARAMETER, levelId);
		parameters.put(BackEndHttpHandler.SESSION_KEY_PARAMETER, sessionKey);
		parameters.put(BackEndHttpHandler.SCORE_PARAMETER, score);
		return parameters;
	}

	/**
	 * Method where parse the Parameters from the HighScoreList Request
	 *
	 * @param httpExchange
	 * @return
	 * @throws NotValidHttpException
	 */
	private static Map<String, String> parseHighScoreListParameters(HttpExchange httpExchange) {
		validHttpMethod(httpExchange, "GET");
		Map<String, String> parameters = new HashMap<String, String>();
		String uri = httpExchange.getRequestURI().toString();
		String levelId = uri.split("/")[1];
		parameters.put(BackEndHttpHandler.REQUEST_PARAMETER, BackEndHttpHandler.HIGH_SCORE_LIST_REQUEST);
		parameters.put(BackEndHttpHandler.LEVEL_ID_PARAMETER, levelId);
		return parameters;
	}

	/**
	 * Method to validate if the request use the correct HttpMethod (GET or
	 * POST), is not throws an Exception
	 *
	 * @param httpExchange
	 * @param method
	 * @throws NotValidHttpException
	 */
	private static void validHttpMethod(HttpExchange httpExchange, String method) {
		if (!method.equalsIgnoreCase(httpExchange.getRequestMethod())) {
		}
	}

	/**
	 * Method used to handle an Exception into an Http Response
	 *
	 * @param message
	 * @param httpExchange
	 * @param statusCode
	 * @throws IOException
	 */
	private void exceptionHandledResponse(String message, HttpExchange httpExchange, int statusCode)
			throws IOException {
		httpExchange.sendResponseHeaders(statusCode, message.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(message.getBytes());
		os.close();
	}

	@Override
	public String description() {
		return "Manage the Http Requests.";
	}
}
