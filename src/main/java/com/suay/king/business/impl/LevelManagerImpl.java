package com.suay.king.business.impl;

import com.suay.king.business.LevelManager;
import com.suay.king.data.DataSingleton;
import com.suay.king.exception.BusinessException;
import com.suay.king.exception.LevelNotFoundException;
import com.suay.king.model.GameLevel;
import com.suay.king.model.UserScore;

public class LevelManagerImpl implements LevelManager {

	public void addUserScore(String sessionKey, int levelId, int scoreValue) {

		// TODO FIND USER ID IN

		UserScore score = new UserScore(1, levelId, scoreValue);

		GameLevel level = DataSingleton.INSTANCE.getGameLevels().putIfAbsent(score.getLevelId(),
				new GameLevel(score.getLevelId()));
		level.getHighScores().add(score);

		// TODO limit 15
	}

	public GameLevel getLevelRanking(Integer levelId) throws BusinessException {
		GameLevel level = DataSingleton.INSTANCE.getGameLevels().get(levelId);
		if (level == null) {
			throw new LevelNotFoundException();
		}
		return level;
	}

}
