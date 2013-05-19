package com.webcommunity.client.page.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.events.EventEntry;

public class EventsViewImpl extends Composite implements EventsView {

	interface EventsViewImplUiBinder extends UiBinder<Widget, EventsViewImpl> {
	}

	private static EventsViewImplUiBinder uiBinder = GWT.create(EventsViewImplUiBinder.class);
	
	private static DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat("dd/MM-yyyy");
	
	private Presenter presenter;

	@UiField FlexTable eventsTable;
	@UiField Button addDialog;


	public EventsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.presenter.getAllEvents();
	}

	@Override
	public void updateEvents(EventEntry[] result) {
		eventsTable.removeAllRows();

		eventsTable.addStyleName("tableList");
		eventsTable.getRowFormatter().addStyleName(0, "tableListHeader");

		eventsTable.getColumnFormatter().addStyleName(0, "eventsListDateColumn");
		eventsTable.setText(0, 0, "Dato");
		eventsTable.setText(0, 1, "Begivenhed");
		
		if (presenter.getUserRole() >= UserRole.SUPER_USER) {
			eventsTable.getColumnFormatter().addStyleName(2, "eventsListDeleteColumn");
			eventsTable.setText(0, 2, "Slet");
		}
		
		for (EventEntry eventEntry : result) {
			int row = eventsTable.getRowCount() ;
			eventsTable.setText(row, 0, DATE_TIME_FORMAT.format(eventEntry.getDate()));
			eventsTable.setText(row, 1, eventEntry.getDescription());
			if (presenter.getUserRole() >= UserRole.SUPER_USER) {
				eventsTable.setWidget(row, 2, createRemoveButton(eventEntry));
			}
		}
		
		addDialog.setVisible(presenter.getUserRole() >= UserRole.SUPER_USER);
	}

	@UiHandler("addDialog")
	protected void onAddDialogClick(ClickEvent event) {
		AddEventDialog dialog = new AddEventDialog(presenter);
		dialog.center();
	}
	
	private Button createRemoveButton(EventEntry eventEntry) {
		Button button = new Button("X");
		if (eventEntry.getKey() == null) {
			button.setEnabled(false);
		} else {
			button.addClickHandler(new RemoveClickHandler(presenter, eventEntry.getKey()));
		}
		return button;
	}
	
	private static final class RemoveClickHandler implements ClickHandler {

		private Presenter presenter;
		private String key;
		
		public RemoveClickHandler(Presenter presenter, String key) {
			this.presenter = presenter;
			this.key = key;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			presenter.deleteEvent(key);
		}
	}
}
