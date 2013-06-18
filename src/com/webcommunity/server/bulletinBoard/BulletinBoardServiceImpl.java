package com.webcommunity.server.bulletinBoard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webcommunity.client.page.bulletinBoard.service.BulletinBoardService;
import com.webcommunity.server.MailDispatchQueue;
import com.webcommunity.server.UserManager;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.bulletinBoard.PostingEntry;
import com.webcommunity.shared.users.UserEntry;

public class BulletinBoardServiceImpl extends RemoteServiceServlet implements BulletinBoardService {

	private static final long serialVersionUID = -808276156138014124L;
	
	private static final int POSTING_PAGE_COUNT = 5;

	private static final Key ANCHESTOR_KEY = KeyFactory.createKey("PostingAnchestor", 8463129400489549065l);
	
	private static final String ENTRY_KIND = "PostingEntry";
	private static final String DATE = "date";
	private static final String USER = "user";
	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	
	private DatastoreService datastoreService = null;

	
	@Override
	public PostingEntry[] getPostings(String sessionId, Integer page) {
		DatastoreService datastore = getDatastoreService();

		Query query = new Query(ENTRY_KIND, ANCHESTOR_KEY);
		query.addSort(DATE, SortDirection.DESCENDING);
		int offset = (page != null && page.intValue() > 0) ? page.intValue() * POSTING_PAGE_COUNT : 0;
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withOffset(offset).limit(POSTING_PAGE_COUNT));

		int lastPostingOffset = getPostingCount(datastore) - 1;
		
		List<PostingEntry> result = new ArrayList<PostingEntry>();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			String keyString = KeyFactory.keyToString(e.getKey());
			result.add(new PostingEntry(keyString,
							(Date)e.getProperty(DATE),
							getDisplayName(sessionId, (String)e.getProperty(USER)),
							(String)e.getProperty(TITLE),
							((Text)e.getProperty(CONTENT)).getValue(),
							lastPostingOffset == (offset + i)));
		}
		
		return result.toArray(new PostingEntry[result.size()]);
	}

	@Override
	public PostingEntry[] addNewPosting(String sessionId, PostingEntry postingEntry) {
		if (hasUserRole(sessionId, UserRole.NORMAL)) {
			Entity entity = new Entity(ENTRY_KIND, ANCHESTOR_KEY);
			entity.setProperty(DATE, getDateNow());
			entity.setProperty(USER, getUserId(sessionId));
			entity.setProperty(TITLE, postingEntry.getTitle());
			entity.setProperty(CONTENT, new Text(postingEntry.getContent()));

			DatastoreService datastore = getDatastoreService();
			datastore.put(entity);
			MailDispatchQueue.enqueuePostingToUsers(postingEntry);
		}

		return getPostings(sessionId, null);
	}
	
	private String getDisplayName(String sessionId, String identifier) {
		if (hasUserRole(sessionId, UserRole.NORMAL)) {
			UserEntry userEntry = UserManager.getUserById(identifier);
			if (userEntry != null) {
				return userEntry.getDisplayName();
			}
		}
		
		return null;
	}
	
	private int getPostingCount(DatastoreService datastore) {
		Query query = new Query(ENTRY_KIND, ANCHESTOR_KEY);
		return datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults());
	}

	private Date getDateNow() {
		return Calendar.getInstance().getTime();
	}
	
	private String getUserId(String sessionId) {
		UserEntry userEntry = UserManager.getUserBySessionId(sessionId);
		return (userEntry != null) ? userEntry.getIdentifier() : null;
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
