package com.webcommunity.client.page.documents.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("documentsService")
public interface DocumentsService extends RemoteService {

	String[] getDocumentLinks(String sessionId);
	
}
