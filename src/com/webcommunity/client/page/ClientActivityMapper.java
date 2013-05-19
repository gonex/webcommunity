package com.webcommunity.client.page;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardActivity;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardPlace;
import com.webcommunity.client.page.events.EventsActivity;
import com.webcommunity.client.page.events.EventsPlace;
import com.webcommunity.client.page.useredit.UserEditActivity;
import com.webcommunity.client.page.useredit.UserEditPlace;
import com.webcommunity.client.page.users.UsersActivity;
import com.webcommunity.client.page.users.UsersPlace;
import com.webcommunity.client.page.welcome.WelcomeActivity;
import com.webcommunity.client.page.welcome.WelcomePlace;

public class ClientActivityMapper implements ActivityMapper {
    private ClientFactory clientFactory;

    public ClientActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof WelcomePlace) {
            return new WelcomeActivity((WelcomePlace) place, clientFactory);
        } else if (place instanceof BulletinBoardPlace) {
            return new BulletinBoardActivity((BulletinBoardPlace) place, clientFactory);
        } else if (place instanceof EventsPlace) {
            return new EventsActivity((EventsPlace) place, clientFactory);
        } else if (place instanceof UsersPlace) {
            return new UsersActivity((UsersPlace) place, clientFactory);
        } else if (place instanceof UserEditPlace) {
        	return new UserEditActivity((UserEditPlace) place, clientFactory);
        }
        
        return null;
    }
}
