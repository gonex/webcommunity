package com.webcommunity.client;

import com.webcommunity.shared.authorization.SessionInfo;

public interface ClientSessionManager {

	void setSessionInfo(SessionInfo sessionInfo);

	SessionInfo getSessionInfo();
	
	String getSessionId();
	
	Long getUserRole();
	
	String getDisplayName();
	
	void addSessionListener(Listener listener);
	
	void removeSessionListener(Listener listener);

	public interface Listener {
		void sessionChanged(SessionInfo sessionInfo);
	}
}
