package com.webcommunity.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardView;
import com.webcommunity.client.page.events.EventsView;
import com.webcommunity.client.page.useredit.UserEditView;
import com.webcommunity.client.page.users.UsersView;
import com.webcommunity.client.page.welcome.WelcomeView;
import com.webcommunity.client.widget.authorization.AuthorizationWidget;
import com.webcommunity.client.widget.navigation.NavigationWidget;

public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
	ClientSessionManager getClientSessionManager();
	AuthorizationWidget getAuthorizationWidget();
    NavigationWidget getNavigationWidget();
    WelcomeView getWelcomeView();
	BulletinBoardView getBulletinBoardView();
    EventsView getEventsView();
    UsersView getUsersView();
	UserEditView getUserEditView();
}