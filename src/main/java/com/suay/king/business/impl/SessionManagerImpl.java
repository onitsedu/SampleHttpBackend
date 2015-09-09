package com.suay.king.business.impl;

import java.util.ArrayList;

import com.suay.king.business.SessionManager;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.repository.datasource.DataSingleton;
import com.suay.king.repository.model.UserSession;

public class SessionManagerImpl implements SessionManager {

	private long sessionDuration;

	public SessionManagerImpl(Long sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public synchronized void addSession(UserSession session) {
		DataSingleton.INSTANCE.getSessionActives().putIfAbsent(
				session.getSessionId(), session);
	}

	public UserSession getUserSession(String session)
			throws SessionExpiredException {
		if (!DataSingleton.INSTANCE.getSessionActives().containsKey(session)) {
			throw new SessionExpiredException();
		} else {
			UserSession userSession = DataSingleton.INSTANCE
					.getSessionActives().get(session);
			if (System.currentTimeMillis() - userSession.getSessionTime() >= sessionDuration) {
				DataSingleton.INSTANCE.getSessionActives().remove(session);
				throw new SessionExpiredException();
			} else {
				return userSession;
			}
		}
	}

	public synchronized void cleanExpiredSessions() {
		long now = System.currentTimeMillis();
		for (UserSession session : new ArrayList<UserSession>(
				DataSingleton.INSTANCE.getSessionActives().values())) {
			if ((now - session.getSessionTime()) > sessionDuration) {
				DataSingleton.INSTANCE.getSessionActives().remove(
						session.getSessionId());
			}
		}
	}

}
