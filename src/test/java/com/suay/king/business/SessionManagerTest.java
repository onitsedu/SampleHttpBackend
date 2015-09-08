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
		sessionManager = new SessionManagerImpl(500L);
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
		UserSession requestedSession = sessionManager.getUserSession(session
				.getSessionId());
		Assert.assertEquals(session, requestedSession);
	}

	@Test
	public void sessionExpired() throws InterruptedException {
		UserSession session = new UserSession(2);
		sessionManager.addSession(session);
		UserSession requestedSession = null;
		Boolean expired = false;
		try {
			Thread.sleep(700L);
			requestedSession = sessionManager.getUserSession(session
					.getSessionId());
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
			Thread.sleep(700L);
			sessionManager.getUserSession(session.getSessionId());
		} catch (SessionExpiredException e) {
			expired = true;
		}
		Assert.assertNull(DataSingleton.INSTANCE.getSessionActives().get(
				session.getSessionId()));
		Assert.assertTrue(expired);
	}

	@Test
	public void sessionCleaner() throws InterruptedException {
		UserSession session = new UserSession(1);
		sessionManager.addSession(session);

		session = new UserSession(2);
		sessionManager.addSession(session);

		session = new UserSession(3);
		sessionManager.addSession(session);

		session = new UserSession(4);
		sessionManager.addSession(session);

		session = new UserSession(5);
		sessionManager.addSession(session);

		Thread.sleep(1000L);

		sessionManager.cleanExpiredSessions();
		Assert.assertTrue(DataSingleton.INSTANCE.getSessionActives().size() == 0);
	}
}
