package com.webcommunity.client.page.documents;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


public class DocumentsPlace extends Place {

	public DocumentsPlace() {
	}
	
    public static class Tokenizer implements PlaceTokenizer<DocumentsPlace> {
        @Override
        public String getToken(DocumentsPlace place) {
            return "";
        }

        @Override
        public DocumentsPlace getPlace(String token) {
            return new DocumentsPlace();
        }
    }
}
