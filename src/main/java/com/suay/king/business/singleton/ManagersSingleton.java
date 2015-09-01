package com.suay.king.business.singleton;

import com.suay.king.business.SessionManager;
import com.suay.king.business.impl.SessionManagerImpl;

public enum ManagersSingleton {

	INSTANCE;

	private SessionManager sessionManager;

	private ManagersSingleton() {
		this.sessionManager = new SessionManagerImpl();
	}

	/**
	 * @return the sessionManager
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

}
