/*
 *   ExpiredSessionScheduler.java
 * 
 * Copyright(c) 2014 Christian Delgado. All Rights Reserved.
 * This software is the proprietary information of Christian Delgado
 * 
 */
package com.suay.king.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.suay.king.business.SessionManager;
import com.suay.king.business.singleton.ManagersSingleton;
import com.suay.king.utils.Constants;

public class ExpiredSessionScheduler implements Runnable {

	private final SessionManager sessionManager;

	public ExpiredSessionScheduler() {
		this.sessionManager = ManagersSingleton.INSTANCE.getSessionManager();
	}

	public void startService() {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(this, Constants.SESSION_EXPIRATION_TIME, Constants.SESSION_EXPIRATION_TIME,
				TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		sessionManager.cleanExpiredSessions();
	}
}
