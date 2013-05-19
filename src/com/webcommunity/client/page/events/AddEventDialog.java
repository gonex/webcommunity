package com.webcommunity.client.page.events;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.webcommunity.client.page.events.EventsView.Presenter;
import com.webcommunity.shared.events.EventEntry;

public class AddEventDialog extends PopupPanel {

	public AddEventDialog(final Presenter presenter) {
		super();
		setModal(true);
		setAutoHideOnHistoryEventsEnabled(true);
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setVisible(true);
		setWidget(absolutePanel);
		absolutePanel.setSize("400px", "187px");
		
		Label lblHeader = new Label("Tilf\u00F8j begivenhed");
		absolutePanel.add(lblHeader, 10, 10);
		lblHeader.setSize("181px", "18px");
		
		Grid grid = new Grid(2, 2);
		absolutePanel.add(grid, 10, 40);
		grid.setSize("380px", "100px");
		
		Label lblDate = new Label("Dato:");
		grid.setWidget(0, 0, lblDate);
		
		final DateBox dateBox = new DateBox();
		dateBox.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM-yyyy")));
		grid.setWidget(0, 1, dateBox);
		dateBox.setWidth("120px");
		
		Label lblEvent = new Label("Begivenhed:");
		grid.setWidget(1, 0, lblEvent);
		grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		final TextBox textBox = new TextBox();
		grid.setWidget(1, 1, textBox);
		textBox.setWidth("260px");
		grid.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		grid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		
		Button btnSave = new Button("Save button");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (dateBox.getValue() != null) {
					presenter.addNewEvent(new EventEntry(dateBox.getValue(), textBox.getText()));
					hide();
				}
			}
		});
		btnSave.setText("Gem");
		absolutePanel.add(btnSave, 10, 146);
		btnSave.setSize("81px", "30px");
		
		Button btnCancel = new Button("Cancel button");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnCancel.setText("Annuller");
		absolutePanel.add(btnCancel, 97, 146);
		btnCancel.setSize("81px", "30px");
	}
}
