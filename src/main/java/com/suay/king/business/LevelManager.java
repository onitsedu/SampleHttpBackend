package com.suay.king.business;

import com.suay.king.exception.BusinessException;
import com.suay.king.model.GameLevel;
import com.suay.king.model.UserScore;

public interface LevelManager {

	public void addUserScore(UserScore score);

	public GameLevel getLevelRanking(Integer levelId) throws BusinessException;
	
	
}
