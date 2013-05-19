package com.webcommunity.client.page.useredit.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webcommunity.shared.users.UserEntry;

@RemoteServiceRelativePath("userEditService")
public interface UserEditService extends RemoteService {

	UserEntry getUser(String sessionId);
	
	UserEntry updateUser(String sessionId, UserEntry userEntry) throws Exception;

	UserEntry createUser(String sessionId, UserEntry userEntry)	throws Exception;
}
