package com.webcommunity.server;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.users.UserEntry;

public class UserManager {

	private static final Key ANCHESTOR_KEY = KeyFactory.createKey("UserAnchestor", 3752134632694530846l);
	
	private static final String ENTRY_KIND = "UserEntry";
	private static final String SESSIONID = "sessionId";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FIRSTNAME = "firstname";
	private static final String LASTNAME = "lastname";
	private static final String ADDRESS = "address";
	private static final String EMAIL = "email";
	private static final String PHONE = "phone";
	private static final String BIRTHDATE = "birthdate";
	private static final String USERROLE = "userRole";
	private static final String SUBSCRIBE = "subscribe";
	
	private static DatastoreService datastoreService = null;

	
	public static UserEntry[] getAllUsers() {
		DatastoreService datastore = getDatastoreService();

		Query query = new Query(ENTRY_KIND, ANCHESTOR_KEY);
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		UserEntry[] result = new UserEntry[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			result[i] = entityToUserEntry(entities.get(i));
		}
		
		return result;
	}
	
	public static UserEntry getUserByUsername(String username) {
		ensureAdminUser();
		return getUser(USERNAME, username);
	}
	
	public static UserEntry getUserBySessionId(String sessionId) {
		return getUser(SESSIONID, sessionId);
	}

	public static UserEntry getUserById(String identifier) {
		return (identifier != null) ? getUser("__key__", KeyFactory.stringToKey(identifier)) : null;
	}

	public static void updateUser(UserEntry userEntry) {
		DatastoreService datastore = getDatastoreService();

		Entity entity = new Entity(KeyFactory.stringToKey(userEntry.getIdentifier()));
		entity.setProperty(SESSIONID, userEntry.getSessionId());
		entity.setProperty(USERNAME, userEntry.getUsername());
		entity.setProperty(PASSWORD, StringCrypto.encrypt(userEntry.getPassword()));
		entity.setProperty(FIRSTNAME, userEntry.getFirstname());
		entity.setProperty(LASTNAME, userEntry.getLastname());
		entity.setProperty(ADDRESS, userEntry.getAddress());
		entity.setProperty(EMAIL, userEntry.getEmail());
		entity.setProperty(PHONE, userEntry.getPhone());
		entity.setProperty(BIRTHDATE, userEntry.getBirthdate());
		entity.setProperty(USERROLE, userEntry.getUserRole());
		entity.setProperty(SUBSCRIBE, userEntry.getSubscribe());
		datastore.put(entity);
	}
	
	public static void createUser(UserEntry userEntry) {
		DatastoreService datastore = getDatastoreService();

		Entity entity = new Entity(ENTRY_KIND, ANCHESTOR_KEY);
		entity.setProperty(USERNAME, userEntry.getUsername());
		entity.setProperty(PASSWORD, StringCrypto.encrypt(userEntry.getPassword()));
		entity.setProperty(FIRSTNAME, userEntry.getFirstname());
		entity.setProperty(LASTNAME, userEntry.getLastname());
		entity.setProperty(ADDRESS, userEntry.getAddress());
		entity.setProperty(EMAIL, userEntry.getEmail());
		entity.setProperty(PHONE, userEntry.getPhone());
		entity.setProperty(BIRTHDATE, userEntry.getBirthdate());
		entity.setProperty(USERROLE, userEntry.getUserRole());
		entity.setProperty(SUBSCRIBE, userEntry.getSubscribe());
		datastore.put(entity);
	}	
	
	private static UserEntry getUser(String property, Object value) {
		if (value != null) {
			DatastoreService datastore = getDatastoreService();
	
			Query query = new Query(ENTRY_KIND, ANCHESTOR_KEY);
			query.setFilter(new Query.FilterPredicate(property, FilterOperator.EQUAL, value));
			List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
			
			if (!entities.isEmpty()) {
				Entity entity = entities.iterator().next();
				return entityToUserEntry(entity);
			}
		}
		return null;
	}
	
	private static UserEntry entityToUserEntry(Entity entity) {
		String keyString = KeyFactory.keyToString(entity.getKey());
		return new UserEntry(keyString,
				(String)entity.getProperty(SESSIONID),
				(String)entity.getProperty(USERNAME),
				StringCrypto.decrypt((String)entity.getProperty(PASSWORD)),
				(String)entity.getProperty(FIRSTNAME),
				(String)entity.getProperty(LASTNAME),
				(String)entity.getProperty(ADDRESS),
				(String)entity.getProperty(EMAIL),
				(String)entity.getProperty(PHONE),
				(Date)entity.getProperty(BIRTHDATE),
				(Long)entity.getProperty(USERROLE),
				(Boolean)entity.getProperty(SUBSCRIBE));
	}
	
	private static void ensureAdminUser() {
		DatastoreService datastore = getDatastoreService();
		
		Query query = new Query(ENTRY_KIND, ANCHESTOR_KEY);
		query.setFilter(new Query.FilterPredicate(USERROLE, FilterOperator.GREATER_THAN_OR_EQUAL, UserRole.ADMINISTRATOR));
		int count = datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults());
		if (count <= 0) {
			Entity entity = new Entity(ENTRY_KIND, ANCHESTOR_KEY);
			entity.setProperty(USERNAME, "admin");
			entity.setProperty(PASSWORD, StringCrypto.encrypt("admin"));
			entity.setProperty(FIRSTNAME, "Administrator");
			entity.setProperty(USERROLE, UserRole.ADMINISTRATOR);
			entity.setProperty(SUBSCRIBE, Boolean.TRUE);
			datastore.put(entity);
		}
	}
	
	private static DatastoreService getDatastoreService() {
		if (datastoreService == null) {
			datastoreService = DatastoreServiceFactory.getDatastoreService();
		}
		
		return datastoreService;
	}
}
