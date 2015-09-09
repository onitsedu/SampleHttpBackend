package com.suay.king;

import java.util.logging.Logger;

import com.suay.king.server.GameServer;
import com.suay.king.server.impl.GameServerImpl;
import com.suay.king.utils.Constants;

public class MainGameServer {
    private static final Logger LOGGER = Logger.getLogger(MainGameServer.class.getName());

    public static void main(String[] args) throws Exception {
        final Integer port = calculatePort(args);
        buildGameServer().startServer(port);
    }

    private static Integer calculatePort(String[] args) {
        if (args.length == 1) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.warning("Error parsing arguments.");
                LOGGER.warning(e.getMessage());
            }
        } else {
            LOGGER.warning("Usage--> java -jar KingMiniGameBackend-X.X.jar [portNumber](optional)");
        }

        LOGGER.warning("Using default port " + Constants.PORT);
        return Constants.PORT;
    }

    private static GameServer buildGameServer() {
        return new GameServerImpl();
    }
}
