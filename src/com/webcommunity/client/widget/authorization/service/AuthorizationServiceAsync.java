package com.webcommunity.client.widget.authorization.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webcommunity.shared.authorization.SessionInfo;

public interface AuthorizationServiceAsync {

	void loginUser(String username, String password, AsyncCallback<SessionInfo> callback);
	
	void restoreSession(String sessionId, AsyncCallback<SessionInfo> callback);
	
	void logoutUser(String sessionId, AsyncCallback<Void> callback);
}
