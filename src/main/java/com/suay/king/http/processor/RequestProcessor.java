package com.suay.king.http.processor;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * @author csuay
 *
 */
@SuppressWarnings("restriction")
public interface RequestProcessor {

    /**
     * Process the specified HttpRequest and writes the response
     * 
     * @param httpExchange
     * @throws IOException
     */
    void processRequest(HttpExchange httpExchange) throws IOException;

}
