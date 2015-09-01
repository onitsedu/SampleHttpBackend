package com.suay.king.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.suay.king.model.UserSession;

public enum DataSingleton {

	INSTANCE;

	private ConcurrentMap<String, UserSession> sessionActives;

	private DataSingleton() {
		this.sessionActives = new ConcurrentHashMap<String, UserSession>();

	}

	/**
	 * @return the sessionActives
	 */
	public ConcurrentMap<String, UserSession> getSessionActives() {
		return sessionActives;
	}

}
