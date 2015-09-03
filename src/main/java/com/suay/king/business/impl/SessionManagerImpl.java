package com.suay.king.business.impl;

import com.suay.king.business.SessionManager;
import com.suay.king.data.DataSingleton;
import com.suay.king.exception.SessionExpiredException;
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
			if (System.currentTimeMillis() - userSession.getSessionTime() >= Constants.SESSION_EXPITATION_TIME) {
				DataSingleton.INSTANCE.getSessionActives().remove(session);
				throw new SessionExpiredException();
			} else {
				return userSession;
			}
		}
	}

}
