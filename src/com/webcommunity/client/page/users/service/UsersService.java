package com.webcommunity.client.page.users.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webcommunity.shared.users.UserEntry;

@RemoteServiceRelativePath("usersService")
public interface UsersService extends RemoteService {

	UserEntry[] getAllUsers(String sessionId);
}
