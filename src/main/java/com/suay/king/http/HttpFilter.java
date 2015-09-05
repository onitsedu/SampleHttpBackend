package com.suay.king.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.suay.king.exception.http.BadRequestException;
import com.suay.king.exception.http.HttpException;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpFilter extends Filter {

	@Override
	public String description() {
		return Constants.HTTP_FILTER_DESCRIPTION;
	}

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		try {
			httpExchange.setAttribute(Constants.HTTP_ATT_BODY, getBody(httpExchange));
			httpExchange.setAttribute(Constants.HTTP_ATT_PARAMS, parseQueryString(httpExchange));
			httpExchange.setAttribute(Constants.HTTP_ATT_PATH, getPathParams(httpExchange));
			chain.doFilter(httpExchange);
		} catch (HttpException e) {
			e.printStackTrace();
			httpExchange.sendResponseHeaders(e.getHttpCode(), e.getHttpMessage().length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(e.getHttpMessage().getBytes());
			os.close();
		}
	}

	private String getBody(HttpExchange httpExchange) throws IOException {
		String line;
		InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer();
		line = bufferedReader.readLine();
		try {
			while (line != null) {
				sb.append(line);
				line = bufferedReader.readLine();
			}
		} finally {
			inputStreamReader.close();
			bufferedReader.close();
		}
		return sb.toString();
	}

	private Map<String, String> parseQueryString(HttpExchange httpExchange) throws BadRequestException {
		String uri = httpExchange.getRequestURI().toString();
		Map<String, String> params = new HashMap<String, String>();
		try {
			if (uri.contains(Constants.HTTP_QS_BEGIN)) {
				String uriParams = uri.substring(uri.lastIndexOf(Constants.HTTP_QS_BEGIN)+1);
				String[] paramsArray = uriParams.split(Constants.HTTP_QS_SEPARATOR);

				for (int i = 0; i < paramsArray.length; i++) {
					String[] qs = paramsArray[i].split(Constants.HTTP_QS_PARAM_SEP);
					params.put(qs[0], qs[1]);
				}
			}
		} catch (Exception e) {
			throw new BadRequestException();
		}
		return params;
	}

	private Map<Integer, String> getPathParams(HttpExchange httpExchange) throws BadRequestException {
		Map<Integer, String> pathParams = new HashMap<Integer, String>();
		String uri = httpExchange.getRequestURI().toString();
		try {
			String[] pathParmArray = uri.split(Constants.HTTP_PATH_SEPARATOR);
			for (int i = 0; i < pathParmArray.length; i++) {
				pathParams.put(i, pathParmArray[i]);
			}
		} catch (Exception e) {
			throw new BadRequestException();
		}
		return pathParams;
	}

}
