package com.suay.king.business.impl;

import java.util.concurrent.ConcurrentSkipListSet;

import com.suay.king.business.LevelManager;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.repository.datasource.DataSingleton;
import com.suay.king.repository.model.GameLevel;
import com.suay.king.repository.model.UserScore;
import com.suay.king.utils.Constants;

/**
 * 
 * @author csuay
 *
 */
public class LevelManagerImpl implements LevelManager {

    public void addUserScore(UserScore score) {
	GameLevel level;
	level = DataSingleton.INSTANCE.getGameLevels().get(score.getLevelId());
	if (level == null) {
	    level = new GameLevel(score.getLevelId());
	    DataSingleton.INSTANCE.getGameLevels().putIfAbsent(score.getLevelId(), level);
	}
	addScore(level, score);
    }

    public GameLevel getLevelRanking(Integer levelId) throws LevelNotFoundException {
	GameLevel level = DataSingleton.INSTANCE.getGameLevels().get(levelId);
	if (level == null) {
	    throw new LevelNotFoundException();
	}
	return level;
    }

    /**
     * Adds the user's score to the ranking if is one of the first 15 and is the
     * best score of the user
     * 
     * @param level
     *            the level where the score will be added
     * @param score
     *            the score to add
     */
    public void addScore(GameLevel level, UserScore score) {
	if (level.getSize().get() >= Constants.LEVEL_MAX_SCORES) {
	    if ((level.getHighScores().last().getScore() < score.getScore())) {
		if (!addOrReplace(score, level.getHighScores())) {
		    level.getHighScores().pollLast();
		}
	    }
	} else {
	    addOrReplace(score, level.getHighScores());
	    level.getSize().incrementAndGet();
	}
    }

    /**
     * adds a score to the highScoresList, if the user is already in the table,
     * replaces his score, otherwise, adds it to the list.
     * 
     * @param score
     *            the score to add
     * @param highScores
     *            the ConcurrentSkipListSet of the best scores
     * 
     * @return true if user is already in the list, false otherwise
     */
    private boolean addOrReplace(UserScore score, ConcurrentSkipListSet<UserScore> highScores) {
	for (UserScore userScore : highScores) {
	    if (userScore.equals(score)) {
		if (userScore.getScore() < score.getScore()) {
		    highScores.remove(userScore);
		    highScores.add(score);
		}
		return true;
	    }
	}
	highScores.add(score);
	return false;
    }

}
