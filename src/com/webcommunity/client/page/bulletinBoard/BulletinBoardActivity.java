package com.webcommunity.client.page.bulletinBoard;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.webcommunity.client.ClientFactory;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.client.page.bulletinBoard.service.BulletinBoardService;
import com.webcommunity.client.page.bulletinBoard.service.BulletinBoardServiceAsync;
import com.webcommunity.shared.bulletinBoard.PostingEntry;


public class BulletinBoardActivity extends AbstractActivity implements BulletinBoardView.Presenter {

	private static final Logger log = Logger.getLogger(BulletinBoardActivity.class.getName());
	
	private BulletinBoardServiceAsync bulletinBoardService = GWT.create(BulletinBoardService.class);
	
	private BulletinBoardPlace place;
	private ClientFactory clientFactory;
	private BulletinBoardView bulletinBoardView;

	
    public BulletinBoardActivity(BulletinBoardPlace place, ClientFactory clientFactory) {
    	this.place = place;
        this.clientFactory = clientFactory;
    }
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
        bulletinBoardView = clientFactory.getBulletinBoardView();
        bulletinBoardView.setPresenter(this);
        panel.setWidget(bulletinBoardView.asWidget());
	}

	@Override
	public void getPostings() {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		bulletinBoardService.getPostings(sessionId, place.getPage(), new AsyncCallback<PostingEntry[]>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "get all events failure", caught);
			}

			@Override
			public void onSuccess(PostingEntry[] result) {
				bulletinBoardView.updatePostings(result);
			}
		});
	}
	
	@Override
	public void addNewPosting(PostingEntry postingEntry) {
    	String sessionId = clientFactory.getClientSessionManager().getSessionId();
		bulletinBoardService.addNewPosting(sessionId, postingEntry, new AsyncCallback<PostingEntry[]>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageDialog.showError(caught.getMessage());
				log.log(Level.SEVERE, "add new posting failure", caught);
			}

			@Override
			public void onSuccess(PostingEntry[] result) {
				bulletinBoardView.goToStart();
			}
		});
	}
	
	@Override
	public Integer getPreviusPage() {
		Integer page = place.getPage();
		return (page != null && page > 1) ? page - 1 : null;
	}
	
	@Override
	public Integer getCurrentPage() {
		Integer page = place.getPage();
		return (page != null && page > 0) ? page : null;
	}

	@Override
	public Integer getNextPage() {
		Integer page = place.getPage();
		return (page == null || page < 1) ? 1 : page + 1;
	}

	@Override
    public Long getUserRole() {
    	return clientFactory.getClientSessionManager().getUserRole();
    }
}
