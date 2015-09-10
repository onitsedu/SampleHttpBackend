package com.suay.king.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.suay.king.business.impl.GameManagerImpl;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.repository.model.GameLevel;
import com.suay.king.repository.model.UserScore;

/**
 * 
 * @author csuay
 *
 */
public class GameManagerTest {

    private GameManager gameManager;

    @Before
    public void setupHighScoreStore() {
	gameManager = GameManagerImpl.getInstance();
    }

    @Test
    public void testLoginUser() {
	String sessionKey = gameManager.login(1);
	assertNotNull(sessionKey);
    }

    @Test
    public void testPostScore() throws SessionExpiredException, LevelNotFoundException {
	String sessionKey = gameManager.login(1);
	assertNotNull(sessionKey);
	gameManager.addScore(sessionKey, 1, 1000);
	GameLevel level = gameManager.listLevelRanking(1);
	UserScore score = level.getHighScores().pollFirst();
	assertNotNull(level);
	assertNotNull(score);
	assertTrue(score.getScore() == 1000);
    }

    @Test
    public void testPostScoresInSeveralLevels() throws SessionExpiredException, LevelNotFoundException {
	String sessionKey = gameManager.login(1);
	assertNotNull(sessionKey);
	gameManager.addScore(sessionKey, 1, 1000);
	GameLevel level = gameManager.listLevelRanking(1);
	assertNotNull(level);
	assertTrue(level.getHighScores().pollFirst().getScore() == 1000);
	assertNotNull(sessionKey);
	gameManager.addScore(sessionKey, 2, 2000);
	level = gameManager.listLevelRanking(2);
	assertNotNull(level);
	assertTrue(level.getHighScores().pollFirst().getScore() == 2000);
	assertNotNull(sessionKey);
	gameManager.addScore(sessionKey, 3, 3000);
	level = gameManager.listLevelRanking(3);
	assertNotNull(level);
	assertTrue(level.getHighScores().pollFirst().getScore() == 3000);
    }

    @Test
    public void testSortedRanking() throws SessionExpiredException, LevelNotFoundException {
	String sessionKey = gameManager.login(1);
	String sessionKey2 = gameManager.login(2);
	String sessionKey3 = gameManager.login(3);
	String sessionKey4 = gameManager.login(4);

	assertNotNull(sessionKey);
	assertNotNull(sessionKey2);
	assertNotNull(sessionKey3);
	assertNotNull(sessionKey4);

	gameManager.addScore(sessionKey, 2, 1000);
	gameManager.addScore(sessionKey2, 2, 2000);
	gameManager.addScore(sessionKey3, 2, 3000);
	gameManager.addScore(sessionKey4, 2, 4000);

	GameLevel level = gameManager.listLevelRanking(2);
	assertNotNull(level);
	UserScore user4Score = level.getHighScores().pollFirst();
	UserScore user3Score = level.getHighScores().pollFirst();
	UserScore user2Score = level.getHighScores().pollFirst();
	UserScore user1Score = level.getHighScores().pollFirst();

	assertTrue(user4Score.getUserId() == 4);
	assertTrue(user4Score.getScore() == 4000);

	assertTrue(user3Score.getUserId() == 3);
	assertTrue(user3Score.getScore() == 3000);

	assertTrue(user2Score.getUserId() == 2);
	assertTrue(user2Score.getScore() == 2000);

	assertTrue(user1Score.getUserId() == 1);
	assertTrue(user1Score.getScore() == 1000);

    }

    @Test
    public void testNotDuplicatedUserScore() throws SessionExpiredException, LevelNotFoundException {
	String sessionKey = gameManager.login(1);

	assertNotNull(sessionKey);

	gameManager.addScore(sessionKey, 3, 1000);
	gameManager.addScore(sessionKey, 3, 2000);

	GameLevel level = gameManager.listLevelRanking(3);
	assertNotNull(level);
	UserScore userScore = level.getHighScores().pollFirst();

	assertTrue(userScore.getUserId() == 1);
	assertTrue(userScore.getScore() == 2000);
	assertNull(level.getHighScores().pollFirst());

    }
}
