package com.webcommunity.client.page.users;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.client.page.users.service.UsersService;
import com.webcommunity.client.page.users.service.UsersServiceAsync;
import com.webcommunity.shared.users.UserEntry;

public class UsersActivity extends AbstractActivity implements UsersView.Presenter {

	private static final Logger log = Logger.getLogger(UsersActivity.class.getName());
	
	private UsersServiceAsync usersService = GWT.create(UsersService.class);
	
    private ClientFactory clientFactory;
	private UsersView usersView;

	public UsersActivity(UsersPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
        usersView = clientFactory.getUsersView();
        usersView.setPresenter(this);
        panel.setWidget(usersView.asWidget());
	}

	@Override
	public void getAllUsers() {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		usersService.getAllUsers(sessionId, new AsyncCallback<UserEntry[]>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "get all users failure", caught);
			}

			@Override
			public void onSuccess(UserEntry[] result) {
				usersView.updateUsers(result);
			}
		});
	}
	
    @Override
    public Long getUserRole() {
    	return clientFactory.getClientSessionManager().getUserRole();
    }
}
