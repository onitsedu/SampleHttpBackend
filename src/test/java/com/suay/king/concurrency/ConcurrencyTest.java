package com.suay.king.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.suay.king.business.GameManager;
import com.suay.king.business.impl.GameManagerImpl;
import com.suay.king.exception.BaseException;

/**
 * 
 * @author csuay
 *
 */
public class ConcurrencyTest {
    private static final Logger LOGGER = Logger.getLogger(ConcurrencyTest.class.getName());

    public static void main(String[] args) {
	testConcurrency();
    }

    private static void testConcurrency() {
	LOGGER.info("Starting test");
	long initTime = System.currentTimeMillis();
	ExecutorService executorService = Executors.newFixedThreadPool(10);
	GameManager gameManager = GameManagerImpl.getInstance();
	int threads = 10;
	int iterations = 100000;
	for (int i = 0; i < threads; i++) {
	    TestScore userRunnable = new TestScore(gameManager, iterations);
	    executorService.submit(userRunnable);
	}
	executorService.shutdown();
	boolean terminated = false;
	while (!terminated) {
	    try {
		terminated = executorService.awaitTermination(1000, TimeUnit.SECONDS);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	long endTime = System.currentTimeMillis();
	LOGGER.info("Test finished");
	LOGGER.info("Test with " + threads + " threads and " + iterations + " iterations each done in "
		+ (double) (endTime - initTime) / 1000 + "s");
	System.exit(0);
    }

    private static class TestScore implements Runnable {
	private final GameManager gameManager;
	private final int nIterations;

	private TestScore(GameManager gameManager, int nIterations) {
	    this.gameManager = gameManager;
	    this.nIterations = nIterations;
	}

	@Override
	public void run() {
	    for (int i = 1; i < nIterations; i++) {
		int userId = i;
		int levelId = nIterations - i;
		int score = new Random().nextInt(5000);
		String sessionKey;
		sessionKey = gameManager.login(userId);
		try {
		    gameManager.addScore(sessionKey, levelId, score);
		    gameManager.listLevelRanking(levelId);
		} catch (BaseException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

}
