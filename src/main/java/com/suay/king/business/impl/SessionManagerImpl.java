package com.suay.king.business.impl;

import java.util.ArrayList;

import com.suay.king.business.SessionManager;
import com.suay.king.data.DataSingleton;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.model.UserSession;
import com.suay.king.utils.Constants;

public class SessionManagerImpl implements SessionManager {

	public void addSession(UserSession session) {
		DataSingleton.INSTANCE.getSessionActives().putIfAbsent(session.getSessionId(), session);
	}

	public UserSession getUserSession(String session) throws SessionExpiredException {
		if (!DataSingleton.INSTANCE.getSessionActives().containsKey(session)) {
			throw new SessionExpiredException();
		} else {
			UserSession userSession = DataSingleton.INSTANCE.getSessionActives().get(session);
			if (System.currentTimeMillis() - userSession.getSessionTime() >= Constants.SESSION_EXPIRATION_TIME) {
				DataSingleton.INSTANCE.getSessionActives().remove(session);
				throw new SessionExpiredException();
			} else {
				return userSession;
			}
		}
	}

	public synchronized void cleanExpiredSessions() {
		long now = System.currentTimeMillis();
		for (UserSession session : new ArrayList<UserSession>(DataSingleton.INSTANCE.getSessionActives().values())) {
			if ((now - session.getSessionTime()) > Constants.SESSION_EXPIRATION_TIME) {
				DataSingleton.INSTANCE.getSessionActives().remove(session.getSessionId());
			}
		}
	}

}
