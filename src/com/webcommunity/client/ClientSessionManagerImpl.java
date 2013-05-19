package com.webcommunity.client;

import java.util.ArrayList;
import java.util.List;

import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.authorization.SessionInfo;

public class ClientSessionManagerImpl implements ClientSessionManager {

	private SessionInfo sessionInfo;
	private List<Listener> listeners;
	
	
	public ClientSessionManagerImpl() {
		listeners = new ArrayList<Listener>();
	}
	
	@Override
	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
		notifyListeners(sessionInfo);
	}

	@Override
	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	@Override
	public String getSessionId() {
		return (sessionInfo != null) ? sessionInfo.getSessionId() : null;
	}

	@Override
	public Long getUserRole() {
		return (sessionInfo != null) ? sessionInfo.getUserRole() : UserRole.NONE;
	}

	@Override
	public String getDisplayName() {
		return (sessionInfo != null) ? sessionInfo.getDisplayName() : null;
	}

	@Override
	public void addSessionListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeSessionListener(Listener listener) {
		listeners.remove(listener);
	}
	
	private void notifyListeners(SessionInfo sessionInfo) {
		for (Listener listener : listeners) {
			listener.sessionChanged(sessionInfo);
		}
	}
}
