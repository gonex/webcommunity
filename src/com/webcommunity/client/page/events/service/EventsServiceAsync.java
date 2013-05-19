package com.webcommunity.client.page.events.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webcommunity.shared.events.EventEntry;

public interface EventsServiceAsync {

	void addNewEvent(String sessionId, EventEntry eventEntry, AsyncCallback<EventEntry[]> callback);
	void getAllEvents(String sessionId, AsyncCallback<EventEntry[]> callback);
	void deleteEvent(String sessionId, String key, AsyncCallback<EventEntry[]> callback);
}
