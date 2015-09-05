package com.suay.king.business;

import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.model.UserSession;

public interface SessionManager {

	public void addSession(UserSession session);

	public UserSession getUserSession(String sessionId) throws SessionExpiredException;

	public void cleanExpiredSessions();

}
