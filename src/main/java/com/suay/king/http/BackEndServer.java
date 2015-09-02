/*
 *   BackEndServer.java
 * 
 * Copyright(c) 2014 Christian Delgado. All Rights Reserved.
 * This software is the proprietary information of Christian Delgado
 * 
 */
package com.suay.king.http;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * Main Class where the HttpServer for the BackEnd is deployed.
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/28/14
 */
public class BackEndServer {

	public static int PORT = 8081;

	/**
	 * Main Method where the HttpServer is deployed The HttpPort can be change,
	 * running the app with the argument [-p portNumber]
	 *
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception {
		// validating the Java Arguments
		if (args.length > 0) {
			try {
				if (args.length == 2) {
					if (args[0].equals("-p")) {
						PORT = Integer.parseInt(args[1]);
					} else {
					}
				} else {
				}
			} catch (Exception e) {
				System.err.println("Error with the arguments.");
				System.err.println(e.getMessage());
				System.err.println("java -jar KingGameServer.jar [-p portNumber]");
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
			HttpContext httpContext = httpServer.createContext("/", new BackEndHttpHandler());
			httpContext.getFilters().add(new BackEndHttpFilter());
			ExecutorService executorService = Executors.newCachedThreadPool();
			httpServer.setExecutor(executorService);
			httpServer.start();
			System.out.println("   HTTPServer started in http://" + hostName + ":" + PORT + "/");
			System.out.println("   Started HTTPServer Successfully!\n");
		} catch (Exception e) {
			System.err.println("Error with the HTTPServer.");
			System.err.println(e.getMessage());
			// e.printStackTrace();
			// throw new BackEndException(e);
		}
	}
}
