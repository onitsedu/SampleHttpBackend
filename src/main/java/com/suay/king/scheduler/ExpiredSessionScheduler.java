package com.suay.king.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.suay.king.business.singleton.ManagersSingleton;
import com.suay.king.utils.Constants;
/**
 * 
 * @author csuay
 *
 */
public class ExpiredSessionScheduler implements Runnable {

	public void startService() {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(this, Constants.SESSION_EXPIRATION_TIME, Constants.SESSION_EXPIRATION_TIME,
				TimeUnit.MILLISECONDS);
	}

	public void run() {
		ManagersSingleton.INSTANCE.getSessionManager().cleanExpiredSessions();
	}
}
