package com.suay.king.business.singleton;

import com.suay.king.business.GameManager;
import com.suay.king.business.LevelManager;
import com.suay.king.business.SessionManager;
import com.suay.king.business.impl.LevelManagerImpl;
import com.suay.king.business.impl.SessionManagerImpl;

public enum ManagersSingleton {

	INSTANCE;

	private SessionManager sessionManager;

	private LevelManager levelManager;

	private GameManager gameManager;

	private ManagersSingleton() {
		this.sessionManager = new SessionManagerImpl();
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
