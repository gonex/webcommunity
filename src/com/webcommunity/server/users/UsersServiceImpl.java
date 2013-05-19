package com.webcommunity.server.users;

import java.util.Arrays;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webcommunity.client.page.users.service.UsersService;
import com.webcommunity.server.UserManager;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.users.UserEntry;

public class UsersServiceImpl extends RemoteServiceServlet implements UsersService {

	private static final long serialVersionUID = -2188086815720760285L;

	
	@Override
	public UserEntry[] getAllUsers(String sessionId) {
		UserEntry[] result = UserManager.getAllUsers();
		
		Arrays.sort(result, new UserComparator());
				
		for (UserEntry userEntry : result) {
			userEntry.setBirthdate(null);
			userEntry.setPassword(null);
			userEntry.setSessionId(null);
			userEntry.setUsername(null);
			userEntry.setUserRole(null);
			if (!hasPermission(sessionId)) {
				userEntry.setLastname(null);
				userEntry.setEmail(null);
				userEntry.setPhone(null);
			}
		}
		
		return result;
	}

	private boolean hasPermission(String sessionId) {
		UserEntry user = UserManager.getUserBySessionId(sessionId);
		if (user != null) {
			return user.getUserRole() > UserRole.NONE;
		}
		
		return false;
	}
}
