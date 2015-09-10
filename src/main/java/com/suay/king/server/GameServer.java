package com.suay.king.server;

/**
 * 
 * @author csuay
 *
 */
public interface GameServer {

    /**
     * Starts the server in the specified port
     * 
     * @param port
     */
    void startServer(Integer port);

    /**
     * stops the server
     */
    void stopServer();

}
