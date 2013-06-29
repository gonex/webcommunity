package com.webcommunity.client.page.documents.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DocumentsServiceAsync {

	 void getDocumentLinks(String sessionId, AsyncCallback<String[]> asyncCallback);
}
