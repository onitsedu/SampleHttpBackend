package com.suay.king.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class GameLevel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int levelId;
	private ConcurrentSkipListSet<UserScore> highScores;

	public GameLevel(Integer levelId) {
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
				sb.append(" , ");
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
