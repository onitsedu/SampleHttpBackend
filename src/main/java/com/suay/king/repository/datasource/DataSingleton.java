package com.suay.king.repository.datasource;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.suay.king.repository.model.GameLevel;
import com.suay.king.repository.model.UserSession;
import com.suay.king.utils.Constants;

/**
 * 
 * @author csuay
 *
 */
public enum DataSingleton {

    INSTANCE;

    private ConcurrentMap<String, UserSession> sessionActives;

    private ConcurrentMap<Integer, GameLevel> gameLevels;

    DataSingleton() {
	this.sessionActives = new ConcurrentHashMap<String, UserSession>();
	this.gameLevels = new ConcurrentHashMap<Integer, GameLevel>(Constants.LEVEL_MAX_SCORES);
    }

    /**
     * @return the sessionActives
     */
    public ConcurrentMap<String, UserSession> getSessionActives() {
	return sessionActives;
    }

    /**
     * @return the gameLevels
     */
    public ConcurrentMap<Integer, GameLevel> getGameLevels() {
	return gameLevels;
    }

}
