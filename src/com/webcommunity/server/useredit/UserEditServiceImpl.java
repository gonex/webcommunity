package com.webcommunity.server.useredit;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webcommunity.client.page.useredit.service.UserEditService;
import com.webcommunity.server.MailDispatchQueue;
import com.webcommunity.server.UserManager;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.users.UserEntry;

public class UserEditServiceImpl extends RemoteServiceServlet implements UserEditService {

	private static final long serialVersionUID = 7976126272175171425L;

	@Override
	public UserEntry getUser(String sessionId) {
		return UserManager.getUserBySessionId(sessionId);
	}

	@Override
	public UserEntry updateUser(String sessionId, UserEntry userEntry) throws Exception {
		UserEntry oldUserEntry = checkUserSession(sessionId);
		checkUserValues(userEntry);
		
		UserManager.updateUser(userEntry);
		MailDispatchQueue.enqueueUserInformationToUsers(oldUserEntry, userEntry);
		return userEntry;
	}

	@Override
	public UserEntry createUser(String sessionId, UserEntry userEntry) throws Exception {
		isAdministrator(sessionId);
		checkUserValues(userEntry);
		
		UserManager.createUser(userEntry);
		return userEntry;
	}
	
	private boolean isAdministrator(String sessionId) {
		UserEntry user = UserManager.getUserBySessionId(sessionId);
		if (user != null) {
			return user.getUserRole() > UserRole.SUPER_USER;
		}
		
		return false;
	}
	
	private UserEntry checkUserSession(String sessionId) throws Exception {
		UserEntry userEntry = UserManager.getUserBySessionId(sessionId);
		if (!sessionId.equals(userEntry.getSessionId())) {
			throw new Exception("Ugyldig sessions id.");
		}
		return userEntry;
	}

	private void checkUserValues(UserEntry userEntry) throws Exception {
		if (userEntry.getUsername().length() < UserEntry.MINIMUM_USER_AND_PASS_LENGTH) {
			throw new Exception("Brugernavnet er for kort");
		}
		
		if (userEntry.getPassword().length() < UserEntry.MINIMUM_USER_AND_PASS_LENGTH) {
			throw new Exception("Kodeordet er for kort");
		}

		UserEntry existingUser = UserManager.getUserByUsername(userEntry.getUsername());
		if (existingUser != null && !userEntry.getIdentifier().equals(existingUser.getIdentifier())) {
			throw new Exception("Brugernavnet er optaget");
		}
	}
}
