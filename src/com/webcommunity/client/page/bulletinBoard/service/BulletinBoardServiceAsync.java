package com.webcommunity.client.page.bulletinBoard.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webcommunity.shared.bulletinBoard.PostingEntry;

public interface BulletinBoardServiceAsync {

	void getPostings(String sessionId, Integer page, AsyncCallback<PostingEntry[]> asyncCallback);

	void addNewPosting(String sessionId, PostingEntry postingEntry, AsyncCallback<PostingEntry[]> asyncCallback);
}
