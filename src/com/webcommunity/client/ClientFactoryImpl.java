package com.webcommunity.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardView;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardViewImpl;
import com.webcommunity.client.page.documents.DocumentsView;
import com.webcommunity.client.page.documents.DocumentsViewImpl;
import com.webcommunity.client.page.events.EventsView;
import com.webcommunity.client.page.events.EventsViewImpl;
import com.webcommunity.client.page.useredit.UserEditView;
import com.webcommunity.client.page.useredit.UserEditViewImpl;
import com.webcommunity.client.page.users.UsersView;
import com.webcommunity.client.page.users.UsersViewImpl;
import com.webcommunity.client.page.welcome.WelcomeView;
import com.webcommunity.client.page.welcome.WelcomeViewImpl;
import com.webcommunity.client.widget.authorization.AuthorizationWidget;
import com.webcommunity.client.widget.authorization.AuthorizationWidgetImpl;
import com.webcommunity.client.widget.navigation.NavigationWidget;
import com.webcommunity.client.widget.navigation.NavigationWidgetImpl;


public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);
    private final ClientSessionManager clientSessionManager = new ClientSessionManagerImpl();
    private final AuthorizationWidget authorizationWidget = new AuthorizationWidgetImpl(this);
    private final NavigationWidget navigationWidget = new NavigationWidgetImpl(placeController);
    private final WelcomeView welcomeView = new WelcomeViewImpl();
    private final BulletinBoardView bulletinBoardView = new BulletinBoardViewImpl(placeController);
    private final EventsView eventsView = new EventsViewImpl();
    private final UsersView usersView = new UsersViewImpl(placeController);
    private final UserEditView userEditView = new UserEditViewImpl();
    private final DocumentsView documentsView = new DocumentsViewImpl();
    
    
    @Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public ClientSessionManager getClientSessionManager() {
		return clientSessionManager;
	}
	
	@Override
	public AuthorizationWidget getAuthorizationWidget() {
		return authorizationWidget;
	}
	
	@Override
	public NavigationWidget getNavigationWidget() {
		return navigationWidget;
	}

	@Override
	public WelcomeView getWelcomeView() {
		return welcomeView;
	}

	@Override
	public BulletinBoardView getBulletinBoardView() {
		return bulletinBoardView;
	}
	
	@Override
	public EventsView getEventsView() {
		return eventsView;
	}

	@Override
	public UsersView getUsersView() {
		return usersView;
	}

	@Override
	public UserEditView getUserEditView() {
		return userEditView;
	}

	@Override
	public DocumentsView getDocumentsView() {
		return documentsView;
	}
}
