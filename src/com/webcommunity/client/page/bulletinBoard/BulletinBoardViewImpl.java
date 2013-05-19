package com.webcommunity.client.page.bulletinBoard;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.bulletinBoard.PostingEntry;

public class BulletinBoardViewImpl extends Composite implements BulletinBoardView {

	interface BulletinBoardViewImplUiBinder extends	UiBinder<Widget, BulletinBoardViewImpl> {
	}

	private static BulletinBoardViewImplUiBinder uiBinder = GWT.create(BulletinBoardViewImplUiBinder.class);

	private static DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat("dd/MM-yyyy");
	
	private PlaceController placeController;
	private Presenter presenter;

	@UiField FlexTable postingsTable;
	@UiField Anchor newerPostings;
	@UiField Button addDialog;
	@UiField Anchor olderPostings;
	

	public BulletinBoardViewImpl(PlaceController placeController) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.placeController = placeController;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.presenter.getPostings();
	}
	
	@UiHandler("newerPostings")
	protected void onNewerPostingsClick(ClickEvent event) {
		placeController.goTo(new BulletinBoardPlace(presenter.getPreviusPage()));
	}
	
	@UiHandler("addDialog")
	protected void onAddDialogClick(ClickEvent event) {
		AddPostingDialog dialog = new AddPostingDialog(presenter);
		dialog.center();
	}

	@UiHandler("olderPostings")
	protected void onOlderPostingsClick(ClickEvent event) {
		placeController.goTo(new BulletinBoardPlace(presenter.getNextPage()));
	}

	@Override
	public void updatePostings(PostingEntry[] result) {
		postingsTable.removeAllRows();
		
		for (PostingEntry postingEntry : result) {
			int row = postingsTable.getRowCount() ;
			postingsTable.setHTML(row, 0, constructHtmlPosting(postingEntry));
		}
		
		newerPostings.setVisible(presenter.getCurrentPage() != null);
		addDialog.setVisible(presenter.getUserRole() > UserRole.NONE);
		olderPostings.setVisible(result.length > 0 && !result[result.length - 1].getIsLast());
	}
	
	@Override
	public void goToStart() {
		placeController.goTo(new BulletinBoardPlace());
	}
	
	private String constructHtmlPosting(PostingEntry postingEntry) {
		return "<h2 class=\"postingTitle\">" + safeGet(postingEntry.getTitle()) + "</h2>" +
			   "<h3 class=\"postingInfo\">" + formatDate(postingEntry.getDate()) + formatUser(postingEntry.getUser()) + "</h3>" +
			   "<p class=\"postingContent\">" + safeGet(postingEntry.getContent()) + "<p>";
	}
	
	private String formatDate(Date date) {
		return "Opslået den " + ((date != null) ? DATE_TIME_FORMAT.format(date) : "?");
	}
	
	private String formatUser(String user) {
		return (user != null) ? " af " + user : "";
	}

	private String safeGet(String string) {
		return string != null ? string : "";
	}
}
