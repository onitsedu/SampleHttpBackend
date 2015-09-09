package com.suay.king.business;

import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.repository.model.UserSession;

public interface SessionManager {

	void addSession(UserSession session);

	UserSession getUserSession(String sessionId) throws SessionExpiredException;

	void cleanExpiredSessions();

}
