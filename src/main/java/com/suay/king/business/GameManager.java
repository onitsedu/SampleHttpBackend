package com.suay.king.business;

import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.repository.model.GameLevel;

/**
 * 
 * @author csuay
 *
 */
public interface GameManager {

    /**
     * Cretes a user session and returns the session key associated to it
     * 
     * @param userId
     * @return String sessionKey
     */
    String login(Integer userId);

    /**
     * Adds a score to the level ranking related to the user associated to the
     * session key
     * 
     * @param sessionKey
     * @param levelId
     * @param score
     * @throws SessionExpiredException
     *             if the sessionKey is timed out
     */
    void addScore(String sessionKey, Integer levelId, Integer score) throws SessionExpiredException;

    /**
     * List the ranking of the specified level
     * 
     * @param levelId
     * @return
     * @throws LevelNotFoundException
     */
    GameLevel listLevelRanking(Integer levelId) throws LevelNotFoundException;

}
