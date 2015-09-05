package com.suay.king.http;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class GameServer {

	public static int PORT = 8080;

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			try {
				PORT = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.err.println("Error parsing arguments.");
				System.err.println(e.getMessage());
				System.err.println("Usage--> java -jar KingMiniGameBackend.jar [portNumber](optional)");
				return;
			}
		}
		try {
			System.out.println("\n\n   Starting HTTPServer.");
			String hostName = "localhost";
			try {
				hostName = InetAddress.getLocalHost().getCanonicalHostName();
			} catch (UnknownHostException ex) {
				System.err.println("Unknown Host: " + ex);
			}
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
			HttpContext httpContext = httpServer.createContext("/", new GameHttpHandler());
			httpContext.getFilters().add(new HttpFilter());
			ExecutorService executorService = Executors.newCachedThreadPool();
			httpServer.setExecutor(executorService);
			httpServer.start();
			System.out.println("   HTTPServer started at http://" + hostName + ":" + PORT + "/");
			System.out.println("   Started HTTPServer Successfully!\n");
		} catch (Exception e) {
			System.err.println("Error with the HTTPServer.");
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
}
