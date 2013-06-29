package com.webcommunity.client.page.documents;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.client.page.documents.service.DocumentsService;
import com.webcommunity.client.page.documents.service.DocumentsServiceAsync;

public class DocumentsActivity extends AbstractActivity implements DocumentsView.Presenter {

	private static final Logger log = Logger.getLogger(DocumentsActivity.class.getName());

	private DocumentsServiceAsync documentsService = GWT.create(DocumentsService.class);

	private ClientFactory clientFactory;
	private DocumentsView documentsView;

    
    public DocumentsActivity(DocumentsPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
        documentsView = clientFactory.getDocumentsView();
        documentsView.setPresenter(this);
        panel.setWidget(documentsView.asWidget());
	}
	
	@Override
	public void getDocumentLinks() {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
    	documentsService.getDocumentLinks(sessionId, new AsyncCallback<String[]>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "get document links failure", caught);
			}

			@Override
			public void onSuccess(String[] result) {
				documentsView.updateDocumentLinks(result);
			}
		});
	}
}
