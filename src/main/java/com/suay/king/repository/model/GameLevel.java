package com.suay.king.repository.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 
 * @author csuay
 *
 */
public class GameLevel implements Serializable {

	/**
	 * Serial for this class version
	 */
	private static final long serialVersionUID = 1601884525834046823L;

	private int levelId;
	private ConcurrentSkipListSet<UserScore> highScores;
	private final AtomicInteger size;

	public GameLevel(Integer levelId) {
		this.size = new AtomicInteger(0);
		this.levelId = levelId;
		this.highScores = new ConcurrentSkipListSet<UserScore>(new UserScore());
	}

	/**
	 * @return the number of scores of the level
	 */
	public AtomicInteger getSize() {
		return size;
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

	/**
	 * two levels are considered equal if their levelId are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof GameLevel)
				&& (((GameLevel) obj).getLevelId() == this.getLevelId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<UserScore> iterator = highScores.iterator(); iterator
				.hasNext();) {
			UserScore userScore = (UserScore) iterator.next();
			sb.append(userScore.getUserId() + "=" + userScore.getScore());
			if (iterator.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
