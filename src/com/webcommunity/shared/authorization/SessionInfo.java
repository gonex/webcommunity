package com.webcommunity.shared.authorization;

import java.io.Serializable;

public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -2741166738365141178L;

	private String sessionId;
	private Long userRole;
	private String displayName;
	
	public SessionInfo() {
	}

	public SessionInfo(String sessionId, Long userRole, String displayName) {
		this.sessionId = sessionId;
		this.userRole = userRole;
		this.displayName = displayName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getUserRole() {
		return userRole;
	}

	public void setUserRole(Long userRole) {
		this.userRole = userRole;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
