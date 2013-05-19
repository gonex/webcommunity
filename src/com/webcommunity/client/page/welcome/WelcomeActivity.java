package com.webcommunity.client.page.welcome;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;

public class WelcomeActivity extends AbstractActivity {
    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
    private ClientFactory clientFactory;

    
    public WelcomeActivity(WelcomePlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        WelcomeView welcomeView = clientFactory.getWelcomeView();
        panel.setWidget(welcomeView.asWidget());
    }
}
