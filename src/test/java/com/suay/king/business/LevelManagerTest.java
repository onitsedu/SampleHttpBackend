package com.suay.king.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.suay.king.business.impl.LevelManagerImpl;
import com.suay.king.exception.business.LevelNotFoundException;
import com.suay.king.model.UserScore;

public class LevelManagerTest {

	private LevelManager levelManager;

	@Before
	public void setupHighScoreStore() {
		levelManager = new LevelManagerImpl();
	}

	@Test
	public void testUniqueUserResult() throws LevelNotFoundException {
		addUserScore(1, 1, 1000);
		Assert.assertEquals("1=1000", levelManager.getLevelRanking(1).toString());
		addUserScore(1, 1, 1300);
		Assert.assertEquals("1=1300", levelManager.getLevelRanking(1).toString());
	}

	@Test
	public void testMaxUserResult() throws LevelNotFoundException {
		addUserScore(1, 3, 1000);
		Assert.assertEquals("1=1000", levelManager.getLevelRanking(3).toString());
		addUserScore(1, 3, 1300);
		Assert.assertEquals("1=1300", levelManager.getLevelRanking(3).toString());
		addUserScore(1, 3, 600);
		Assert.assertEquals("1=1300", levelManager.getLevelRanking(3).toString());
	}

	@Test
	public void testMultipleUserResultSorted() throws LevelNotFoundException {
		addUserScore(1, 2, 1000);
		addUserScore(2, 2, 1300);
		addUserScore(3, 2, 1400);
		Assert.assertEquals("3=1400,2=1300,1=1000", levelManager.getLevelRanking(2).toString());
		addUserScore(1, 2, 1500);
		Assert.assertEquals("1=1500,3=1400,2=1300", levelManager.getLevelRanking(2).toString());
		addUserScore(2, 2, 1600);
		Assert.assertEquals("2=1600,1=1500,3=1400", levelManager.getLevelRanking(2).toString());
	}

	@Test
	public void testMultipleLevelResultSorted() throws LevelNotFoundException {

		addUserScore(1, 4, 1000);
		addUserScore(2, 4, 1300);
		addUserScore(3, 4, 1400);
		Assert.assertEquals("3=1400,2=1300,1=1000", levelManager.getLevelRanking(4).toString());
		addUserScore(1, 5, 1400);
		addUserScore(2, 5, 1100);
		addUserScore(3, 5, 1200);
		Assert.assertEquals("1=1400,3=1200,2=1100", levelManager.getLevelRanking(5).toString());

	}

	private void addUserScore(Integer userId, Integer levelId, Integer score) {
		UserScore userScore = new UserScore(userId, levelId, score);
		levelManager.addUserScore(userScore);
	}
}
