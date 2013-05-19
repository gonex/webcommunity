package com.webcommunity.client.page.useredit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserEditPlace extends Place {

	public UserEditPlace() {
	}
	
	public static class Tokenizer implements PlaceTokenizer<UserEditPlace> {
        @Override
        public String getToken(UserEditPlace place) {
            return "";
        }

        @Override
        public UserEditPlace getPlace(String token) {
            return new UserEditPlace();
        }
    }
}
