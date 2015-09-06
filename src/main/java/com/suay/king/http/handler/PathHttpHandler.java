package com.suay.king.http.handler;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public interface PathHttpHandler {

	void handleRequest(HttpExchange httpExchange);

}
