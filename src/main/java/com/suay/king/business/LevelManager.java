package com.suay.king.business;

import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.repository.model.GameLevel;
import com.suay.king.repository.model.UserScore;

/**
 * 
 * @author csuay
 *
 */
public interface LevelManager {

    /**
     * Adds the userScore to the level specified on it
     * 
     * @param userScore
     */
    void addUserScore(UserScore userScore);

    /**
     * Returns The GameLevel containing the ranking of GameScores
     * 
     * @param levelId
     * @return GameLevel
     * @throws LevelNotFoundException
     */
    GameLevel getLevelRanking(Integer levelId) throws LevelNotFoundException;

}
