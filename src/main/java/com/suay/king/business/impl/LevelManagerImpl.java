package com.suay.king.business.impl;

import com.suay.king.business.LevelManager;
import com.suay.king.data.DataSingleton;
import com.suay.king.exception.BusinessException;
import com.suay.king.exception.LevelNotFoundException;
import com.suay.king.model.GameLevel;
import com.suay.king.model.UserScore;

public class LevelManagerImpl implements LevelManager {

	public void addUserScore(UserScore score) {
		// GameLevel level =
		// DataSingleton.INSTANCE.getGameLevels().putIfAbsent(score.getLevelId(),
		// new GameLevel(score.getLevelId()));
		GameLevel level = new GameLevel(score.getLevelId());
		if (!DataSingleton.INSTANCE.getGameLevels().containsKey(score.getLevelId()))
			level = DataSingleton.INSTANCE.getGameLevels().put(score.getLevelId(), level);
		else
			level = DataSingleton.INSTANCE.getGameLevels().get(score.getLevelId());

		// why this not work equals than putIfAbsent

		level.getHighScores().add(score);

	}

	public GameLevel getLevelRanking(Integer levelId) throws LevelNotFoundException {
		GameLevel level = DataSingleton.INSTANCE.getGameLevels().get(levelId);
		if (level == null) {
			throw new LevelNotFoundException();
		}
		return level;
	}

}
