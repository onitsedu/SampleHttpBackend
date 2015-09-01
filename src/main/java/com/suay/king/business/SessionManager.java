package com.suay.king.business;

import com.suay.king.model.GameUser;
import com.suay.king.model.UserSession;

public interface SessionManager {

	public UserSession addSession(GameUser user);

	public Boolean isSessionExpired(UserSession session);

	public void cleanExpiredSessions();

}
