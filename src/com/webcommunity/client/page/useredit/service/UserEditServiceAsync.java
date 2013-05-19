package com.webcommunity.client.page.useredit.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webcommunity.shared.users.UserEntry;

public interface UserEditServiceAsync {

	void getUser(String sessionId, AsyncCallback<UserEntry> callback);
	
	void updateUser(String sessionId, UserEntry userEntry, AsyncCallback<UserEntry> callback);

	void createUser(String sessionId, UserEntry userEntry, AsyncCallback<UserEntry> callback);
}
