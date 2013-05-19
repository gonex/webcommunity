package com.webcommunity.server.authorization;

import java.util.UUID;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webcommunity.client.widget.authorization.service.AuthorizationService;
import com.webcommunity.server.UserManager;
import com.webcommunity.shared.authorization.SessionInfo;
import com.webcommunity.shared.users.UserEntry;

public class AuthorizationServiceImpl extends RemoteServiceServlet implements AuthorizationService {

	private static final long serialVersionUID = -6109674238267658227L;

	private static final Logger log = Logger.getLogger(AuthorizationServiceImpl.class.getName());
	
	
	@Override
	public SessionInfo loginUser(String username, String password) {
		UserEntry userEntry = UserManager.getUserByUsername(username);
		if (userEntry != null && password.equals(userEntry.getPassword())) {
			String sessionId = UUID.randomUUID().toString();
			userEntry.setSessionId(sessionId);
			UserManager.updateUser(userEntry);
			log.info("Login user " + username);
			return new SessionInfo(sessionId, userEntry.getUserRole(), userEntry.getUsername());
		}
		
		log.warning("Failed login attempt by user " + username);
		return null;
	}
	
	@Override
	public SessionInfo restoreSession(String sessionId) {
		UserEntry userEntry = UserManager.getUserBySessionId(sessionId);
		if (userEntry != null) {
			log.info("Restore session for " + userEntry.getUsername());
			return new SessionInfo(userEntry.getSessionId(), userEntry.getUserRole(), userEntry.getUsername());
		}
		
		return null;
	}

	@Override
	public void logoutUser(String sessionId) {
		UserEntry userEntry = UserManager.getUserBySessionId(sessionId);
		if (userEntry != null) {
			userEntry.setSessionId(null);
			UserManager.updateUser(userEntry);
			log.info("Logout user " + userEntry.getUsername());
		}
	}
}
