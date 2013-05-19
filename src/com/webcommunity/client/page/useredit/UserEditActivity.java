package com.webcommunity.client.page.useredit;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.client.page.useredit.service.UserEditService;
import com.webcommunity.client.page.useredit.service.UserEditServiceAsync;
import com.webcommunity.shared.users.UserEntry;

public class UserEditActivity extends AbstractActivity implements UserEditView.Presenter {

	private static final Logger log = Logger.getLogger(UserEditActivity.class.getName());
	
	private UserEditServiceAsync userEditService = GWT.create(UserEditService.class);
	
    private ClientFactory clientFactory;
	private UserEditView userEditView;
	
	public UserEditActivity(UserEditPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		userEditView = clientFactory.getUserEditView();
		userEditView.setPresenter(this);
        panel.setWidget(userEditView.asWidget());
	}

	@Override
	public void getUser() {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		userEditService.getUser(sessionId, new AsyncCallback<UserEntry>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "get user failure", caught);
			}

			@Override
			public void onSuccess(UserEntry result) {
				userEditView.updateUser(result);
			}
		});
	}

	@Override
	public void updateUser(UserEntry userEntry) {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		userEditService.updateUser(sessionId, userEntry, new AsyncCallback<UserEntry>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "update user failure", caught);
			}

			@Override
			public void onSuccess(UserEntry result) {
				userEditView.updateUser(result);
				MessageDialog.show("Gemt", "Dine ændringer er blevet gemt");

			}
		});
	}
	
	@Override
	public void createUser(UserEntry userEntry) {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		userEditService.createUser(sessionId, userEntry, new AsyncCallback<UserEntry>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "create user failure", caught);
			}

			@Override
			public void onSuccess(UserEntry result) {
				userEditView.updateUser(result);
				MessageDialog.show("Oprettet", "Bruger oprettet");

			}
		});
	}

	@Override
    public Long getUserRole() {
    	return clientFactory.getClientSessionManager().getUserRole();
    }
}
