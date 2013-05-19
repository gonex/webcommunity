package com.webcommunity.client.page.bulletinBoard.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webcommunity.shared.bulletinBoard.PostingEntry;

@RemoteServiceRelativePath("bulletinBoardService")
public interface BulletinBoardService extends RemoteService {

	PostingEntry[] getPostings(String sessionId, Integer page);

	PostingEntry[] addNewPosting(String sessionId, PostingEntry postingEntry);
}
