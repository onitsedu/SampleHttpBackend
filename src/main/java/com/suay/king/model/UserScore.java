package com.suay.king.model;

import java.io.Serializable;
import java.util.Comparator;

public class UserScore implements Comparator<UserScore>, Serializable {

	/**
	 * Serial for this class version
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;

	private Integer levelId;

	private Integer score;

	public UserScore() {

	}

	public UserScore(Integer userId, Integer levelId, Integer score) {
		this.userId = userId;
		this.levelId = levelId;
		this.score = score;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the levelId
	 */
	public Integer getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId
	 *            the levelId to set
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public int compare(UserScore firstScore, UserScore secondScore) {
		if (secondScore.getScore().equals(firstScore.getScore())) {
			return Integer.compare(firstScore.getUserId(),
					secondScore.getUserId());
		} else {
			return Integer.compare(secondScore.getScore(),
					firstScore.getScore());
		}
	}

	/**
	 * Since only one score of each user can be in each level,two UserScores are
	 * equals when the userId and the level are equals
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof UserScore)) {
			return false;
		} else if (this == obj) {
			return true;
		} else {
			UserScore userScore = (UserScore) obj;
			if ((this.userId == userScore.userId)
					&& (this.levelId == userScore.levelId)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public String toString() {
		return userId + "=" + score;
	}

}
