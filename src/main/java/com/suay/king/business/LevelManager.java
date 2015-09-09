package com.suay.king.business;

import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.repository.model.GameLevel;
import com.suay.king.repository.model.UserScore;

public interface LevelManager {

	void addUserScore(UserScore userScore);

	GameLevel getLevelRanking(Integer levelId) throws LevelNotFoundException;

}
