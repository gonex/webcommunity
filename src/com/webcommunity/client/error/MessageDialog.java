package com.webcommunity.client.error;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MessageDialog extends PopupPanel {

	public MessageDialog(String header, String message) {
		super();
		setModal(true);
		setAutoHideOnHistoryEventsEnabled(true);
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		setWidget(absolutePanel);
		absolutePanel.setSize("220px", "120px");
		
		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnNewButton.setText("OK");
		absolutePanel.add(btnNewButton, 10, 80);
		btnNewButton.setSize("81px", "30px");
		
		Label lblHeader = new Label(header);
		absolutePanel.add(lblHeader, 10, 10);
		
		Label lblError = new Label("");
		absolutePanel.add(lblError, 10, 34);
		lblError.setSize("200px", "41px");
		lblError.setText(message);
	}
	
	public static void show(String header, String message) {
		MessageDialog dialog = new MessageDialog(header, message);
		dialog.center();
	}

	public static void showError(String message) {
		MessageDialog dialog = new MessageDialog("Fejl.", message);
		dialog.center();
	}
}
