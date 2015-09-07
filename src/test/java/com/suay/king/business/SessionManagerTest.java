package com.suay.king.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.suay.king.business.impl.SessionManagerImpl;
import com.suay.king.data.DataSingleton;
import com.suay.king.exception.business.SessionExpiredException;
import com.suay.king.model.UserSession;

public class SessionManagerTest {

	private SessionManager sessionManager;

	@Before
	public void setupHighScoreStore() {
		sessionManager = new SessionManagerImpl(100L);
	}

	@Test
	public void addNewSession() {
		UserSession session = new UserSession(1);
		sessionManager.addSession(session);
		Assert.assertNotNull(session.getSessionId());
	}

	@Test
	public void sessionNotExpired() throws SessionExpiredException {
		UserSession session = new UserSession(2);
		sessionManager.addSession(session);
		UserSession requestedSession = sessionManager.getUserSession(session.getSessionId());
		Assert.assertEquals(session, requestedSession);
	}

	@Test
	public void sessionExpired() throws InterruptedException {
		UserSession session = new UserSession(2);
		sessionManager.addSession(session);
		UserSession requestedSession = null;
		Boolean expired = false;
		try {
			Thread.sleep(200L);
			requestedSession = sessionManager.getUserSession(session.getSessionId());
		} catch (SessionExpiredException e) {
			expired = true;
		}
		Assert.assertNull(requestedSession);
		Assert.assertTrue(expired);
	}

	@Test
	public void sessionRemovedFromArray() throws InterruptedException {
		UserSession session = new UserSession(2);
		sessionManager.addSession(session);
		Boolean expired = false;
		try {
			Thread.sleep(200L);
			sessionManager.getUserSession(session.getSessionId());
		} catch (SessionExpiredException e) {
			expired = true;
		}
		Assert.assertNull(DataSingleton.INSTANCE.getSessionActives().get(session.getSessionId()));
		Assert.assertTrue(expired);
	}
	
	public void sessionCleaner(){
		
	}
	
	

}
