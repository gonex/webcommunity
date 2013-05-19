package com.webcommunity.server.events;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webcommunity.client.page.events.service.EventsService;
import com.webcommunity.server.UserManager;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.events.EventEntry;
import com.webcommunity.shared.users.UserEntry;

public class EventsServiceImpl extends RemoteServiceServlet implements EventsService {

	private static final long serialVersionUID = 8586746344249373543L;

	private static final Key ANCHESTOR_KEY = KeyFactory.createKey("EventAnchestor", 2081548735482093854l);
	
	private static final String ENTRY_KIND = "EventEntry";
	private static final String DATE = "date";
	private static final String DESCRIPTION = "description";
	
	private DatastoreService datastoreService = null;
	
	@Override
	public EventEntry[] addNewEvent(String sessionId, EventEntry eventEntry) {
		if (hasUserRole(sessionId, UserRole.SUPER_USER)) {
			Entity entity = new Entity(ENTRY_KIND, ANCHESTOR_KEY);
			entity.setProperty(DATE, eventEntry.getDate());
			entity.setProperty(DESCRIPTION, eventEntry.getDescription());

			DatastoreService datastore = getDatastoreService();
			datastore.put(entity);
		}

		return getAllEvents(sessionId);
	}

	@Override
	public EventEntry[] getAllEvents(String sessionId) {
		DatastoreService datastore = getDatastoreService();

		Query query = new Query(ENTRY_KIND, ANCHESTOR_KEY);
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

		List<EventEntry> result = new ArrayList<EventEntry>();
		for (Entity e : entities) {
			String keyString = KeyFactory.keyToString(e.getKey());
			result.add(new EventEntry(keyString, (Date)e.getProperty(DATE), (String)e.getProperty(DESCRIPTION)));
		}
		
		if (hasUserRole(sessionId, UserRole.NORMAL)) {
			result.addAll(getAllBirthdayEvents());
		}
		
		Collections.sort(result, new Comparator<EventEntry>() {
			@Override
			public int compare(EventEntry arg0, EventEntry arg1) {
				return arg0.getDate().compareTo(arg1.getDate());
			}
		});
		
		return result.toArray(new EventEntry[result.size()]);
	}
	
	@Override
	public EventEntry[] deleteEvent(String sessionId, String key) {
		if (hasUserRole(sessionId, UserRole.SUPER_USER)) {
			DatastoreService datastore = getDatastoreService();
			datastore.delete(KeyFactory.stringToKey(key));
		}

		return getAllEvents(sessionId);
	}
	
	private List<EventEntry> getAllBirthdayEvents() {
		UserEntry[] allUsers = UserManager.getAllUsers();

		Calendar today = Calendar.getInstance();
		int currentYear = today.get(Calendar.YEAR);
		
		List<EventEntry> result = new ArrayList<EventEntry>();
		for (UserEntry userEntry : allUsers) {
			Date birthdate = userEntry.getBirthdate();
			if (birthdate != null) {
				Calendar birthday = Calendar.getInstance();
				birthday.setTime(birthdate);
				int birthdateYear = birthday.get(Calendar.YEAR);
				birthday.set(Calendar.YEAR, currentYear);
				if (birthday.before(today)) {
					birthday.add(Calendar.YEAR, 1);
				}
				int birthdayYear = birthday.get(Calendar.YEAR);
				
				int age = birthdayYear - birthdateYear;
				result.add(new EventEntry(birthday.getTime(), userEntry.getDisplayName() + " har " + age + " års fødselsdag"));
			}
		}
		
		return result;
	}
	
	private boolean hasUserRole(String sessionId, long userRole) {
		UserEntry user = UserManager.getUserBySessionId(sessionId);
		if (user != null) {
			return user.getUserRole() >= userRole;
		}
		
		return false;
	}
	
	private DatastoreService getDatastoreService() {
		if (datastoreService == null) {
			datastoreService = DatastoreServiceFactory.getDatastoreService();
		}
		
		return datastoreService;
	}	
}
