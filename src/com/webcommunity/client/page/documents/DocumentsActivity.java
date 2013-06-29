package com.webcommunity.client.page.documents;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;

public class DocumentsActivity extends AbstractActivity {

    private ClientFactory clientFactory;

    
    public DocumentsActivity(DocumentsPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
        DocumentsView documentsView = clientFactory.getDocumentsView();
        panel.setWidget(documentsView.asWidget());
	}
}
