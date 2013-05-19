package com.webcommunity.client.page.users;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UsersPlace extends Place {

    public UsersPlace() {
    }

	public static class Tokenizer implements PlaceTokenizer<UsersPlace> {
        @Override
        public String getToken(UsersPlace place) {
            return "";
        }

        @Override
        public UsersPlace getPlace(String token) {
            return new UsersPlace();
        }
    }
}
