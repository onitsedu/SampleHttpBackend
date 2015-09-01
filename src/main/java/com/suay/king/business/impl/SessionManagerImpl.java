package com.suay.king.business.impl;

import java.util.ArrayList;

import com.suay.king.business.SessionManager;
import com.suay.king.data.DataSingleton;
import com.suay.king.model.GameUser;
import com.suay.king.model.UserSession;
import com.suay.king.utils.Constants;

public class SessionManagerImpl implements SessionManager {

	public UserSession addSession(GameUser user) {

		UserSession session = new UserSession(user.getUserId());
		DataSingleton.INSTANCE.getSessionActives().putIfAbsent(session.getSessionId(), session);
		return session;
	}

	public Boolean isSessionExpired(UserSession session) {
		Boolean isExpired;
		if (!DataSingleton.INSTANCE.getSessionActives().containsKey(session)) {
			isExpired = true;
		} else {
			Long sessionInitTime = DataSingleton.INSTANCE.getSessionActives().get(session.getSessionId())
					.getSessionTime();
			if (System.currentTimeMillis() - sessionInitTime >= Constants.SESSION_EXPITATION_TIME) {
				DataSingleton.INSTANCE.getSessionActives().remove(session.getSessionId());
				isExpired = true;
			} else {
				isExpired = false;
			}
		}
		return isExpired;
	}

	public synchronized void cleanExpiredSessions() {
		long now = System.currentTimeMillis();
		for (UserSession session : new ArrayList<UserSession>(DataSingleton.INSTANCE.getSessionActives().values())) {
			if ((now - session.getSessionTime()) > Constants.SESSION_EXPITATION_TIME) {
				DataSingleton.INSTANCE.getSessionActives().remove(session.getSessionId());
			}
		}
	}

}
