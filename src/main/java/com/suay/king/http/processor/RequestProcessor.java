package com.suay.king.http.processor;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public interface RequestProcessor {

	void processRequest(HttpExchange httpExchange) throws IOException;

}
