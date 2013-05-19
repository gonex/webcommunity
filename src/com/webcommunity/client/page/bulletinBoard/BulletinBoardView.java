package com.webcommunity.client.page.bulletinBoard;

import com.google.gwt.user.client.ui.IsWidget;
import com.webcommunity.shared.bulletinBoard.PostingEntry;

public interface BulletinBoardView extends IsWidget {

	public void setPresenter(Presenter presenter);
	public void updatePostings(PostingEntry[] result);
	public void goToStart();

    public interface Presenter {
		void getPostings();
		void addNewPosting(PostingEntry postingEntry);
		Integer getPreviusPage();
		Integer getCurrentPage();
		Integer getNextPage();
		Long getUserRole();
    }
}
