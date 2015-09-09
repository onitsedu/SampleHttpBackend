package com.suay.king.repository.model;

import java.io.Serializable;
import java.util.UUID;

public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sessionId;

	private Integer userId;

	private Long sessionTime;

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
