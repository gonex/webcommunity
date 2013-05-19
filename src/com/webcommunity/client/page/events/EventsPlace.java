package com.webcommunity.client.page.events;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EventsPlace extends Place {

    public EventsPlace() {
    }

    public static class Tokenizer implements PlaceTokenizer<EventsPlace> {
        @Override
        public String getToken(EventsPlace place) {
            return "";
        }

        @Override
        public EventsPlace getPlace(String token) {
            return new EventsPlace();
        }
    }
}