package com.webcommunity.client.page.events.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webcommunity.shared.events.EventEntry;

@RemoteServiceRelativePath("eventsService")
public interface EventsService extends RemoteService {

	EventEntry[] addNewEvent(String sessionId, EventEntry eventEntry);
	EventEntry[] getAllEvents(String sessionId);
	EventEntry[] deleteEvent(String sessionId, String key);
}
