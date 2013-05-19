package com.webcommunity.client.page.users.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webcommunity.shared.users.UserEntry;

public interface UsersServiceAsync {

	void getAllUsers(String sessionId, AsyncCallback<UserEntry[]> asyncCallback);
}
