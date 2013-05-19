package com.webcommunity.client.widget.navigation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardPlace;
import com.webcommunity.client.page.events.EventsPlace;
import com.webcommunity.client.page.users.UsersPlace;
import com.webcommunity.client.page.welcome.WelcomePlace;

public class NavigationWidgetImpl extends Composite implements NavigationWidget {

	interface NavigationViewImplUiBinder extends UiBinder<Widget, NavigationWidgetImpl> {
	}

	private static NavigationViewImplUiBinder uiBinder = GWT.create(NavigationViewImplUiBinder.class);

	private PlaceController placeController;

	@UiField Anchor welcome;
	@UiField Anchor bulletinBoard;
	@UiField Anchor events;
	@UiField Anchor users;
	
	
	public NavigationWidgetImpl(PlaceController placeController) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.placeController = placeController;		
	}

	@UiHandler("welcome")
	void onWelcomeClick(ClickEvent event) {
		placeController.goTo(new WelcomePlace());
	}
	
	@UiHandler("bulletinBoard")
	void onBulletinBoardClick(ClickEvent event) {
		placeController.goTo(new BulletinBoardPlace());
	}

	@UiHandler("events")
	void onEventsClick(ClickEvent event) {
		placeController.goTo(new EventsPlace());
	}

	@UiHandler("users")
	void onUsersClick(ClickEvent event) {
		placeController.goTo(new UsersPlace());
	}
}
