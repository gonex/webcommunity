package com.webcommunity.client.page.bulletinBoard;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.webcommunity.client.page.bulletinBoard.BulletinBoardView.Presenter;
import com.webcommunity.shared.bulletinBoard.PostingEntry;


public class AddPostingDialog extends PopupPanel {

	public AddPostingDialog(final Presenter presenter) {
		super();
		setModal(true);
		setAutoHideOnHistoryEventsEnabled(true);
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setVisible(true);
		setWidget(absolutePanel);
		absolutePanel.setSize("500px", "400px");
		
		Label lblHeader = new Label("Nyt opslag");
		absolutePanel.add(lblHeader, 10, 10);
		lblHeader.setSize("181px", "18px");
		
		Label lblTitle = new Label("Overskrift:");
		absolutePanel.add(lblTitle, 10, 60);
		lblHeader.setSize("181px", "18px");
		
		final TextBox textBoxTitle = new TextBox();
		absolutePanel.add(textBoxTitle, 10, 80);
		textBoxTitle.setWidth("470px");

		Label lblContent = new Label("Indhold:");
		absolutePanel.add(lblContent, 10, 130);
		lblContent.setSize("181px", "18px");
		
		final TextArea textAreaContent = new TextArea();
		absolutePanel.add(textAreaContent, 10, 150);
		textAreaContent.setWidth("470px");
		textAreaContent.setHeight("180px");
		textAreaContent.addStyleName("textArea-posting");
		
		Button btnSave = new Button("Save button");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxTitle.getText().length() > 0 && textAreaContent.getText().length() > 0) {
					presenter.addNewPosting(new PostingEntry(textBoxTitle.getText(), textAreaContent.getText()));
					hide();
				}
			}
		});
		btnSave.setText("Opret");
		absolutePanel.add(btnSave, 10, 355);
		btnSave.setSize("81px", "30px");
		
		Button btnCancel = new Button("Cancel button");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnCancel.setText("Annuller");
		absolutePanel.add(btnCancel, 97, 355);
		btnCancel.setSize("81px", "30px");
	}
}
