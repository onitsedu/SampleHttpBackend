package com.suay.king.business;

import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.repository.model.UserSession;

/**
 * 
 * @author csuay
 *
 */
public interface SessionManager {
	/**
	 * Adds a new session related to the user specified on it and fills the
	 * session object with the auto-generated sessionKey
	 * 
	 * @param session
	 */
	void addSession(UserSession session);

	/**
	 * Get the user session related to the sessionKey
	 * 
	 * @param sessionKey
	 * @return UserSession
	 * @throws SessionExpiredException
	 *             if session is out of time
	 */
	UserSession getUserSession(String sessionKey)
			throws SessionExpiredException;

	/**
	 * Task that cleans all the expired sessions from the session structure
	 */
	void cleanExpiredSessions();

}
