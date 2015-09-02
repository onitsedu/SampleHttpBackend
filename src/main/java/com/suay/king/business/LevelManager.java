package com.suay.king.business;

import com.suay.king.exception.BusinessException;
import com.suay.king.model.GameLevel;

public interface LevelManager {

	public void addUserScore(String sessionKey, int levelId, int score);

	public GameLevel getLevelRanking(Integer levelId) throws BusinessException;

}
