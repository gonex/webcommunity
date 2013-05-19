package com.webcommunity.client.page.events;

import com.google.gwt.user.client.ui.IsWidget;
import com.webcommunity.shared.events.EventEntry;

public interface EventsView extends IsWidget {
	
	public void setPresenter(Presenter presenter);
	public void updateEvents(EventEntry[] result);
	
    public interface Presenter {
		void addNewEvent(EventEntry eventEntry);
		void getAllEvents();
		void deleteEvent(String key);
		Long getUserRole();
    }
}