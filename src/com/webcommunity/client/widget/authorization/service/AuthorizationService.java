package com.webcommunity.client.widget.authorization.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webcommunity.shared.authorization.SessionInfo;

@RemoteServiceRelativePath("authorizationService")
public interface AuthorizationService extends RemoteService {
	
	SessionInfo loginUser(String username, String password);

	SessionInfo restoreSession(String sessionId);
	
	void logoutUser(String sessionId);
}
