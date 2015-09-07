package com.suay.king.business.singleton;

import com.suay.king.business.GameManager;
import com.suay.king.business.LevelManager;
import com.suay.king.business.SessionManager;
import com.suay.king.business.impl.LevelManagerImpl;
import com.suay.king.business.impl.SessionManagerImpl;
import com.suay.king.utils.Constants;

public enum ManagersSingleton {

	INSTANCE;

	private SessionManager sessionManager;

	private LevelManager levelManager;

	private GameManager gameManager;

	ManagersSingleton() {
		this.sessionManager = new SessionManagerImpl(Constants.SESSION_EXPIRATION_TIME);
		this.levelManager = new LevelManagerImpl();
	}

	/**
	 * @return the sessionManager
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * @return the levelManager
	 */
	public LevelManager getLevelManager() {
		return levelManager;
	}

	/**
	 * @return the gameManager
	 */
	public GameManager getGameManager() {
		return gameManager;
	}

}
