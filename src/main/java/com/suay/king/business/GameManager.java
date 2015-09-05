package com.suay.king.business;

import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.model.GameLevel;

public interface GameManager {

	public String login(Integer userId);

	public void addScore(String sessionKey, Integer levelId, Integer score) throws SessionExpiredException;

	public GameLevel listLevelRanking(Integer levelId) throws LevelNotFoundException;
}
