package com.webcommunity.client.page.users;

import com.google.gwt.user.client.ui.IsWidget;
import com.webcommunity.shared.users.UserEntry;

public interface UsersView extends IsWidget{

	void setPresenter(Presenter presenter);
	void updateUsers(UserEntry[] result);

	public interface Presenter {
		void getAllUsers();
		Long getUserRole();
    }
}
