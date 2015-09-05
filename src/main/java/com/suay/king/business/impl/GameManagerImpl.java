package com.suay.king.business.impl;

import com.suay.king.business.GameManager;
import com.suay.king.business.LevelManager;
import com.suay.king.business.SessionManager;
import com.suay.king.business.singleton.ManagersSingleton;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.model.GameLevel;
import com.suay.king.model.UserScore;
import com.suay.king.model.UserSession;
import com.suay.king.scheduler.ExpiredSessionScheduler;

public class GameManagerImpl implements GameManager {

	private LevelManager levelManager;
	private SessionManager sessionManager;
	private ExpiredSessionScheduler scheduler;

	private volatile static GameManager instance;

	public static GameManager getInstance() {
		if (instance == null) {
			synchronized (GameManager.class) {
				if (instance == null) {
					instance = new GameManagerImpl();
				}
			}
		}
		return instance;
	}

	private GameManagerImpl() {
		this.levelManager = ManagersSingleton.INSTANCE.getLevelManager();
		this.sessionManager = ManagersSingleton.INSTANCE.getSessionManager();
		this.scheduler = new ExpiredSessionScheduler();
		scheduler.startService();

	}

	public String login(Integer userId) {
		UserSession session = new UserSession(userId);
		sessionManager.addSession(session);
		return session.getSessionId();
	}

	public void addScore(String sessionKey, Integer levelId, Integer score) throws SessionExpiredException {
		UserSession session = sessionManager.getUserSession(sessionKey);
		UserScore userScore = new UserScore(session.getUserId(), levelId, score);
		levelManager.addUserScore(userScore);
	}

	public GameLevel listLevelRanking(Integer levelId) throws LevelNotFoundException {
		return levelManager.getLevelRanking(levelId);
	}

}
