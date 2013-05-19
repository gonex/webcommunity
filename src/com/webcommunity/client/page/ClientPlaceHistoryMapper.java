package com.webcommunity.client.page;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardPlace;
import com.webcommunity.client.page.events.EventsPlace;
import com.webcommunity.client.page.useredit.UserEditPlace;
import com.webcommunity.client.page.users.UsersPlace;
import com.webcommunity.client.page.welcome.WelcomePlace;

@WithTokenizers({WelcomePlace.Tokenizer.class, BulletinBoardPlace.Tokenizer.class, EventsPlace.Tokenizer.class, UsersPlace.Tokenizer.class, UserEditPlace.Tokenizer.class})
public class ClientPlaceHistoryMapper implements PlaceHistoryMapper {

    @Override
    public Place getPlace(String token)
    {
    	String[] tokens = token.split("\\?", 2); 

		if ("Welcome".equals(tokens[0])) {
			return new WelcomePlace();
		} else if ("BulletinBoard".equals(tokens[0])) {
			if (tokens.length > 1) {
				return new BulletinBoardPlace(tokens[1]);
			} else {
				return new BulletinBoardPlace();
			}
		} else if ("Events".equals(tokens[0])) {
			return new EventsPlace();
		} else if ("Users".equals(tokens[0])) {
			return new UsersPlace();
		} else if ("UserEdit".equals(tokens[0])) {
			return new UserEditPlace();
		}

		return new WelcomePlace();
    }

    @Override
    public String getToken(Place place)
    {
        if (place instanceof WelcomePlace) {
            return "Welcome";
        } else if (place instanceof BulletinBoardPlace) {
        	StringBuilder sb = new StringBuilder();
        	sb.append("BulletinBoard");
        	if (((BulletinBoardPlace)place).getPage() != null) {
        		sb.append("?");
        		sb.append(((BulletinBoardPlace)place).getPage());
        	}
            return sb.toString();
        } else if (place instanceof EventsPlace) {
            return "Events";
        } else if (place instanceof UsersPlace) {
            return "Users";
        } else if (place instanceof UserEditPlace) {
            return "UserEdit";
        }
        
        return "Welcome";
    }
}
