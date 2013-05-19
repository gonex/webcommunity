package com.webcommunity.client.page.useredit;

import com.google.gwt.user.client.ui.IsWidget;
import com.webcommunity.shared.users.UserEntry;

public interface UserEditView extends IsWidget {

	void setPresenter(Presenter presenter);
	void updateUser(UserEntry result);

	public interface Presenter {
		void getUser();
		void updateUser(UserEntry userEntry);
		void createUser(UserEntry userEntry);
		Long getUserRole();
    }

}
