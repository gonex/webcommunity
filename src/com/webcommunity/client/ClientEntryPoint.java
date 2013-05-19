package com.webcommunity.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.webcommunity.client.page.ClientActivityMapper;
import com.webcommunity.client.page.ClientPlaceHistoryMapper;
import com.webcommunity.client.page.welcome.WelcomePlace;
import com.webcommunity.client.widget.authorization.AuthorizationWidget;
import com.webcommunity.client.widget.navigation.NavigationWidget;


public class ClientEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        SimplePanel contentPanel = new SimplePanel();

        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new ClientActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(contentPanel);

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        ClientPlaceHistoryMapper historyMapper= GWT.create(ClientPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, new WelcomePlace());

        //TODO: consider making a ClientWidgetFactory that will create the widgets and pass in controllers etc.
        
        AuthorizationWidget authorizationWidget = clientFactory.getAuthorizationWidget();
        NavigationWidget navigationWidget = clientFactory.getNavigationWidget();

        RootPanel.get("authorizationWidget").add(authorizationWidget);
        RootPanel.get("navigationWidget").add(navigationWidget);
        RootPanel.get("contentPanel").add(contentPanel);
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
    }
}
