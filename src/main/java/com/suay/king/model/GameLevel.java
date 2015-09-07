package com.suay.king.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.suay.king.GameServer;
import com.suay.king.utils.Constants;

public class GameLevel implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int levelId;
	private ConcurrentSkipListSet<UserScore> highScores;
	private final AtomicInteger size;

	public GameLevel(Integer levelId) {
		this.size = new AtomicInteger(0);
		this.levelId = levelId;
		this.highScores = new ConcurrentSkipListSet<UserScore>(new UserScore());
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId
	 *            the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	/**
	 * @return the highScores
	 */
	public ConcurrentSkipListSet<UserScore> getHighScores() {
		return highScores;
	}

	public void addScore(UserScore score) {
		if (size.get() >= Constants.LEVEL_MAX_SCORES) {
			if (!(highScores.last().getScore() > score.getScore())) {
				if (!addOrReplace(score)) {
					highScores.pollLast();
				}
			}
		} else {
			addOrReplace(score);
			size.incrementAndGet();
		}
	}

	/**
	 * adds a score to the highScoresList, if the user is already in the table,
	 * replaces his score, if not, add it .
	 * 
	 * @param score
	 * @return true if user is already in the list false otherwise
	 */
	private boolean addOrReplace(UserScore score) {
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

	/**
	 * two levels are considered equal if their levelId are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof GameLevel) && (((GameLevel) obj).getLevelId() == this.getLevelId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<UserScore> iterator = highScores.iterator(); iterator.hasNext();) {
			UserScore userScore = (UserScore) iterator.next();
			sb.append(userScore.getUserId() + "=" + userScore.getScore());
			if (iterator.hasNext()) {
				sb.append(",");
			}
		}
		LOGGER.info(sb.toString());
		return sb.toString();
	}
}
