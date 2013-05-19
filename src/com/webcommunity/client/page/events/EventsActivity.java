package com.webcommunity.client.page.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.client.page.events.service.EventsService;
import com.webcommunity.client.page.events.service.EventsServiceAsync;
import com.webcommunity.shared.events.EventEntry;

public class EventsActivity extends AbstractActivity implements EventsView.Presenter {

	private static final Logger log = Logger.getLogger(EventsActivity.class.getName());

	private EventsServiceAsync eventsService = GWT.create(EventsService.class);
	
    private ClientFactory clientFactory;
	private EventsView eventsView;
    
    
    public EventsActivity(EventsPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        eventsView = clientFactory.getEventsView();
        eventsView.setPresenter(this);
        panel.setWidget(eventsView.asWidget());
    }
        
    @Override
    public void addNewEvent(EventEntry eventEntry) {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		eventsService.addNewEvent(sessionId, eventEntry, new AsyncCallback<EventEntry[]>() {

			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "add new event failure", caught);
			}

			public void onSuccess(EventEntry[] result) {
				eventsView.updateEvents(result);
			}
		});
    }

	@Override
	public void getAllEvents() {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		eventsService.getAllEvents(sessionId, new AsyncCallback<EventEntry[]>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "get all events failure", caught);
			}

			@Override
			public void onSuccess(EventEntry[] result) {
				eventsView.updateEvents(result);
			}
		});
	}
	
    @Override
    public void deleteEvent(String key) {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		eventsService.deleteEvent(sessionId, key, new AsyncCallback<EventEntry[]>() {

			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "delete event failure", caught);
			}

			public void onSuccess(EventEntry[] result) {
				eventsView.updateEvents(result);
			}
		});
    }
    
    @Override
    public Long getUserRole() {
    	return clientFactory.getClientSessionManager().getUserRole();
    }
}
