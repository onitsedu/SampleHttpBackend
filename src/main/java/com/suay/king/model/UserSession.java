package com.suay.king.model;

import java.io.Serializable;
import java.util.UUID;

public class UserSession implements Serializable {

	/**
	 * Serial for this class version
	 */
	private static final long serialVersionUID = 1L;

	private String sessionId;

	private Integer userId;

	private Long sessionTime;

	/**
	 * GameUser session constructor
	 * 
	 * @param userId
	 */
	public UserSession(Integer userId) {
		this.userId = userId;
		this.sessionTime = System.currentTimeMillis();
		this.sessionId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
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
	 * @return the sessionTime
	 */
	public Long getSessionTime() {
		return sessionTime;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

}
