package com.suay.king.business.impl;

import com.suay.king.business.LevelManager;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.repository.datasource.DataSingleton;
import com.suay.king.repository.model.GameLevel;
import com.suay.king.repository.model.UserScore;

public class LevelManagerImpl implements LevelManager {

	public synchronized void addUserScore(UserScore score) {
		GameLevel level;
		level = DataSingleton.INSTANCE.getGameLevels().get(score.getLevelId());
		if (level == null) {
			level = new GameLevel(score.getLevelId());
			DataSingleton.INSTANCE.getGameLevels().putIfAbsent(score.getLevelId(), level);
		}
		level.addScore(score);
	}

	public GameLevel getLevelRanking(Integer levelId) throws LevelNotFoundException {
		GameLevel level = DataSingleton.INSTANCE.getGameLevels().get(levelId);
		if (level == null) {
			throw new LevelNotFoundException();
		}
		return level;
	}

}
