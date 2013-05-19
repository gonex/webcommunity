package com.webcommunity.client.page.bulletinBoard;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BulletinBoardPlace extends Place {

	private Integer page;

	public BulletinBoardPlace() {
		page = null;
	}
	
	public BulletinBoardPlace(Integer page) {
		this.page = page;
	}
	
	public BulletinBoardPlace(String token) {
		this.page = tokenToInt(token);
	}

	public Integer getPage() {
		return page;
	}
	
    private static Integer tokenToInt(String token) {
    	try {
    		Integer value = Integer.valueOf(token);
    		if (value > 0) {
    			return value;
    		}
    	} catch (Exception ex) {
    	}
    	
    	return null;
    }

	public static class Tokenizer implements PlaceTokenizer<BulletinBoardPlace> {
        @Override
        public String getToken(BulletinBoardPlace place) {
        	Integer value = place.getPage();
            return (value != null && value > 0) ? value.toString() : "";
        }

        @Override
        public BulletinBoardPlace getPlace(String token) {
        	return new BulletinBoardPlace(tokenToInt(token));
        }
	}
}
