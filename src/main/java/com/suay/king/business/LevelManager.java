package com.suay.king.business;

import com.suay.king.exception.LevelNotFoundException;
import com.suay.king.model.GameLevel;
import com.suay.king.model.UserScore;

public interface LevelManager {

	public void addUserScore(UserScore userScore);

	public GameLevel getLevelRanking(Integer levelId) throws LevelNotFoundException;

}
