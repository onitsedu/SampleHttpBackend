package com.suay.king.server.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.suay.king.http.filter.HttpFilter;
import com.suay.king.http.handler.GameHttpHandler;
import com.suay.king.server.GameServer;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * 
 * @author csuay
 *
 */
@SuppressWarnings("restriction")
public class GameServerImpl implements GameServer {
    private static final Logger LOGGER = Logger.getLogger(GameServerImpl.class.getName());

    private HttpServer httpServer;

    public void startServer(Integer port) {
	String hostName = resolveHostName();
	try {
	    LOGGER.info("Starting HTTPServer.");
	    doStart(port);
	    LOGGER.info("HTTPServer started at http://" + hostName + ":" + port + "/");
	    LOGGER.info("Started HTTPServer Successfully!\n");
	} catch (IOException e) {
	    LOGGER.warning("Error with the HTTPServer.");
	    LOGGER.warning(e.getMessage());
	}
    }

    public void stopServer() {

	httpServer.stop(0);
    }

    private void doStart(Integer port) throws IOException {
	httpServer = HttpServer.create(new InetSocketAddress(port), 0);
	HttpContext httpContext = httpServer.createContext("/", new GameHttpHandler());
	httpContext.getFilters().add(new HttpFilter());
	httpServer.setExecutor(Executors.newCachedThreadPool());
	httpServer.start();
    }

    private String resolveHostName() {
	String hostName = null;
	try {
	    hostName = InetAddress.getLocalHost().getCanonicalHostName();
	} catch (UnknownHostException ex) {
	    LOGGER.warning("Unknown Host: " + ex);
	}
	return hostName != null ? hostName : Constants.LOCALHOST;
    }

}
