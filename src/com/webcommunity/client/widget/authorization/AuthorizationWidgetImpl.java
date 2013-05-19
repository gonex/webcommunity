package com.webcommunity.client.widget.authorization;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.ClientSessionManager;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.client.widget.authorization.service.AuthorizationService;
import com.webcommunity.client.widget.authorization.service.AuthorizationServiceAsync;
import com.webcommunity.shared.authorization.SessionInfo;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.EventBus;

public class AuthorizationWidgetImpl extends Composite implements AuthorizationWidget, ClientSessionManager.Listener {

	interface AuthorizationWidgetImplUiBinder extends UiBinder<Widget, AuthorizationWidgetImpl> {
	}

	private static AuthorizationWidgetImplUiBinder uiBinder = GWT.create(AuthorizationWidgetImplUiBinder.class);

	private static final String WEB_COMMUNITY_SESSION_ID = "WEBCOMMUNITYSESSIONID";
	private static final Logger log = Logger.getLogger("AuthorizationWidgetImpl");
	
	private AuthorizationServiceAsync authorizationService = GWT.create(AuthorizationService.class);

	private PlaceController placeController;
	private EventBus eventBus;
	private ClientSessionManager clientSessionManager;
	
	@UiField VerticalPanel panelLogon;
	@UiField TextBox txtUsername;
	@UiField PasswordTextBox txtPassword;
	@UiField Button btnLogon;
	@UiField VerticalPanel panelLogoff;
	@UiField Label lblLogoff;
	@UiField Button btnLogoff;


	public AuthorizationWidgetImpl(ClientFactory clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		
		placeController = clientFactory.getPlaceController();
		eventBus = clientFactory.getEventBus();
		
		clientSessionManager = clientFactory.getClientSessionManager();
		clientSessionManager.addSessionListener(this);
		clientSessionManager.setSessionInfo(null);
		
		txtUsername.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13) {
					txtPassword.setFocus(true);
				}
			}
		});
		
		txtPassword.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13) {
					btnLogon.click();
				}
			}
		});

		restoreSession();
	}
	
	@Override
	public void sessionChanged(SessionInfo sessionInfo) {
		panelLogon.setVisible(sessionInfo == null);
		panelLogoff.setVisible(sessionInfo != null);
		if (sessionInfo != null) {
			lblLogoff.setText("Du er logget ind som " + sessionInfo.getDisplayName());
		}
	}
		
	@UiHandler("btnLogon")
	void onBtnLogonClick(ClickEvent event) {
		authorizationService.loginUser(txtUsername.getText(), txtPassword.getText(), new AsyncCallback<SessionInfo>() {
			
			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "logon failure", caught);
			}
			
			@Override
			public void onSuccess(SessionInfo result) {
				if (result != null) {
					clientSessionManager.setSessionInfo(result);
					txtUsername.setText("");
					txtPassword.setText("");
					setCookie(result.getSessionId());
					refreshPlace();
				} else {
					MessageDialog.show("Ugyldig login.", "Forkert brugernavn og/eller kodeord.");
				}
			}
		});
	}
	
	@UiHandler("btnLogoff")
	void onBtnLogoffClick(ClickEvent event) {
		authorizationService.logoutUser(clientSessionManager.getSessionId(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "logoff failure", caught);
			}

			@Override
			public void onSuccess(Void result) {
				clientSessionManager.setSessionInfo(null);
				Cookies.removeCookie(WEB_COMMUNITY_SESSION_ID);
				refreshPlace();
			}
		});
	}
	
	private void restoreSession() {
		String sessionId = Cookies.getCookie(WEB_COMMUNITY_SESSION_ID);
		if (sessionId != null) {
			authorizationService.restoreSession(sessionId, new AsyncCallback<SessionInfo>() {
	
				@Override
				public void onFailure(Throwable caught) {
					MessageDialog.showError(caught.getMessage());
					log.log(Level.SEVERE, "restore session failure", caught);
				}
	
				@Override
				public void onSuccess(SessionInfo result) {
					if (result != null) {
						clientSessionManager.setSessionInfo(result);
						refreshPlace();
						setCookie(result.getSessionId());
					}
				}
			});
		}
	}
	
	private void setCookie(String sessionId) {
		final long DURATION = 1000 * 60 * 60 * 24 * 7; //remembering login 1 week
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie(WEB_COMMUNITY_SESSION_ID, sessionId, expires);
	}
	
	private void refreshPlace() {
		eventBus.fireEvent(new PlaceChangeEvent(placeController.getWhere()));
	}
}
