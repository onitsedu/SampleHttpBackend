package com.suay.king;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.suay.king.http.GameHttpHandler;
import com.suay.king.http.HttpFilter;
import com.suay.king.utils.Constants;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class GameServer {
	private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

	public static void main(String[] args) throws Exception {
		new GameServer().startServer(GameServer.parseArgsPort(args));
	}

	public void startServer(Integer port) {
		String hostName = getHostName();
		try {
			LOGGER.info("Starting HTTPServer.");
			initServer(port);
			LOGGER.info("HTTPServer started at http://" + hostName + ":" + port + "/");
			LOGGER.info("Started HTTPServer Successfully!\n");
		} catch (IOException e) {
			LOGGER.warning("Error with the HTTPServer.");
			LOGGER.warning(e.getMessage());
		}
	}

	private void initServer(Integer port) throws IOException {
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
		HttpContext httpContext = httpServer.createContext("/", new GameHttpHandler());
		httpContext.getFilters().add(new HttpFilter());
		httpServer.setExecutor(Executors.newCachedThreadPool());
		httpServer.start();
	}

	private String getHostName() {
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException ex) {
			LOGGER.warning("Unknown Host: " + ex);
		}
		return hostName != null ? hostName : Constants.LOCALHOST;
	}

	private static Integer parseArgsPort(String[] args) {
		Integer argsport = null;
		if (args.length > 0) {
			try {
				argsport = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				LOGGER.warning("Error parsing arguments.");
				LOGGER.warning(e.getMessage());
				LOGGER.warning("Usage--> java -jar KingMiniGameBackend.jar [portNumber](optional)");
				LOGGER.warning("Using default port " + Constants.PORT);
			}
		}
		return argsport != null ? argsport : Constants.PORT;
	}

}
