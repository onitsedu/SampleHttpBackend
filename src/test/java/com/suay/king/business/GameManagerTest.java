package com.suay.king.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.suay.king.business.impl.GameManagerImpl;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.model.GameLevel;
import com.suay.king.model.UserScore;

public class GameManagerTest {

	private GameManager gameManager;

	@Before
	public void setupHighScoreStore() {
		gameManager = GameManagerImpl.getInstance();
	}

	@Test
	public void testLoginUser() {
		String sessionKey = gameManager.login(1);
		Assert.assertNotNull(sessionKey);
	}

	@Test
	public void testPostScore() throws SessionExpiredException, LevelNotFoundException {
		String sessionKey = gameManager.login(1);
		Assert.assertNotNull(sessionKey);
		gameManager.addScore(sessionKey, 1, 1000);
		GameLevel level = gameManager.listLevelRanking(1);
		UserScore score = level.getHighScores().pollFirst();
		Assert.assertNotNull(level);
		Assert.assertNotNull(score);
		Assert.assertTrue(score.getScore() == 1000);
	}

	@Test
	public void testPostScoresInSeveralLevels() throws SessionExpiredException, LevelNotFoundException {
		String sessionKey = gameManager.login(1);
		Assert.assertNotNull(sessionKey);
		gameManager.addScore(sessionKey, 1, 1000);
		GameLevel level = gameManager.listLevelRanking(1);
		Assert.assertNotNull(level);
		Assert.assertTrue(level.getHighScores().pollFirst().getScore() == 1000);
		Assert.assertNotNull(sessionKey);
		gameManager.addScore(sessionKey, 2, 2000);
		level = gameManager.listLevelRanking(2);
		Assert.assertNotNull(level);
		Assert.assertTrue(level.getHighScores().pollFirst().getScore() == 2000);
		Assert.assertNotNull(sessionKey);
		gameManager.addScore(sessionKey, 3, 3000);
		level = gameManager.listLevelRanking(3);
		Assert.assertNotNull(level);
		Assert.assertTrue(level.getHighScores().pollFirst().getScore() == 3000);
	}

	@Test
	public void testSortedRanking() throws SessionExpiredException, LevelNotFoundException {
		String sessionKey = gameManager.login(1);
		String sessionKey2 = gameManager.login(2);
		String sessionKey3 = gameManager.login(3);
		String sessionKey4 = gameManager.login(4);

		Assert.assertNotNull(sessionKey);
		Assert.assertNotNull(sessionKey2);
		Assert.assertNotNull(sessionKey3);
		Assert.assertNotNull(sessionKey4);

		gameManager.addScore(sessionKey, 2, 1000);
		gameManager.addScore(sessionKey2, 2, 2000);
		gameManager.addScore(sessionKey3, 2, 3000);
		gameManager.addScore(sessionKey4, 2, 4000);

		GameLevel level = gameManager.listLevelRanking(2);
		Assert.assertNotNull(level);
		UserScore user4Score = level.getHighScores().pollFirst();
		UserScore user3Score = level.getHighScores().pollFirst();
		UserScore user2Score = level.getHighScores().pollFirst();
		UserScore user1Score = level.getHighScores().pollFirst();

		Assert.assertTrue(user4Score.getUserId() == 4);
		Assert.assertTrue(user4Score.getScore() == 4000);

		Assert.assertTrue(user3Score.getUserId() == 3);
		Assert.assertTrue(user3Score.getScore() == 3000);

		Assert.assertTrue(user2Score.getUserId() == 2);
		Assert.assertTrue(user2Score.getScore() == 2000);

		Assert.assertTrue(user1Score.getUserId() == 1);
		Assert.assertTrue(user1Score.getScore() == 1000);

	}

	@Test
	public void testNotDuplicatedUserScore() throws SessionExpiredException, LevelNotFoundException {
		String sessionKey = gameManager.login(1);

		Assert.assertNotNull(sessionKey);

		gameManager.addScore(sessionKey, 3, 1000);
		gameManager.addScore(sessionKey, 3, 2000);

		GameLevel level = gameManager.listLevelRanking(3);
		Assert.assertNotNull(level);
		UserScore userScore = level.getHighScores().pollFirst();

		Assert.assertTrue(userScore.getUserId() == 1);
		Assert.assertTrue(userScore.getScore() == 2000);
		Assert.assertNull(level.getHighScores().pollFirst());

	}
}
