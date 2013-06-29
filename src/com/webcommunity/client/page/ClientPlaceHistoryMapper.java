package com.webcommunity.client.page;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardPlace;
import com.webcommunity.client.page.documents.DocumentsPlace;
import com.webcommunity.client.page.events.EventsPlace;
import com.webcommunity.client.page.useredit.UserEditPlace;
import com.webcommunity.client.page.users.UsersPlace;
import com.webcommunity.client.page.welcome.WelcomePlace;

@WithTokenizers({WelcomePlace.Tokenizer.class, BulletinBoardPlace.Tokenizer.class, EventsPlace.Tokenizer.class, UsersPlace.Tokenizer.class, UserEditPlace.Tokenizer.class, DocumentsPlace.Tokenizer.class})
public class ClientPlaceHistoryMapper implements PlaceHistoryMapper {

	private static final String WELCOME = "Welcome";
	private static final String BULLETIN_BOARD = "BulletinBoard";
	private static final String EVENTS = "Events";
	private static final String USERS = "Users";
	private static final String USER_EDIT = "UserEdit";
	private static final String DOCUMENTS = "Documents";

	
    @Override
    public Place getPlace(String token) {
    	String[] tokens = token.split("\\?", 2); 

		if (WELCOME.equals(tokens[0])) {
			return new WelcomePlace();
		} else if (BULLETIN_BOARD.equals(tokens[0])) {
			if (tokens.length > 1) {
				return new BulletinBoardPlace(tokens[1]);
			} else {
				return new BulletinBoardPlace();
			}
		} else if (EVENTS.equals(tokens[0])) {
			return new EventsPlace();
		} else if (USERS.equals(tokens[0])) {
			return new UsersPlace();
		} else if (USER_EDIT.equals(tokens[0])) {
			return new UserEditPlace();
		} else if (DOCUMENTS.equals(tokens[0])) {
			return new DocumentsPlace();
		}

		return new WelcomePlace();
    }

    @Override
    public String getToken(Place place) {
        if (place instanceof WelcomePlace) {
            return WELCOME;
        } else if (place instanceof BulletinBoardPlace) {
        	StringBuilder sb = new StringBuilder();
        	sb.append(BULLETIN_BOARD);
        	if (((BulletinBoardPlace)place).getPage() != null) {
        		sb.append("?");
        		sb.append(((BulletinBoardPlace)place).getPage());
        	}
            return sb.toString();
        } else if (place instanceof EventsPlace) {
            return EVENTS;
        } else if (place instanceof UsersPlace) {
            return USERS;
        } else if (place instanceof UserEditPlace) {
            return USER_EDIT;
        } else if (place instanceof DocumentsPlace) {
        	return DOCUMENTS;
        }
        
        return WELCOME;
    }
}
