package com.webcommunity.client.page.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.webcommunity.client.page.useredit.UserEditPlace;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.users.UserEntry;

public class UsersViewImpl extends Composite implements UsersView {

	interface UsersViewImplUiBinder extends UiBinder<Widget, UsersViewImpl> {
	}
	
	private static UsersViewImplUiBinder uiBinder = GWT.create(UsersViewImplUiBinder.class);

	@UiField FlexTable usersTable;
	@UiField FlexTable emailSelectionsTable;
	@UiField Anchor userEdit;
	
	private Presenter presenter;
	private PlaceController placeController;
	private List<String> emailSelections;

	public UsersViewImpl(PlaceController placeController) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.placeController = placeController;
		this.emailSelections = new ArrayList<String>();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.presenter.getAllUsers();
	}
	
	@Override
	public void updateUsers(UserEntry[] result) {
		emailSelections.clear();
		updateEmailSelections();
		
		usersTable.removeAllRows();

		usersTable.addStyleName("tableList");
		usersTable.getRowFormatter().addStyleName(0, "tableListHeader");

		usersTable.setText(0, 0, "Addresse");
		usersTable.setText(0, 1, "Navn");
		usersTable.setText(0, 2, "Email");
		usersTable.setText(0, 3, "Telefon nr.");
		if (presenter.getUserRole() > UserRole.NONE) {
			usersTable.setText(0, 4, "Vælg");
		}
		
		for (UserEntry userEntry : result) {
			int row = usersTable.getRowCount() ;
			usersTable.setText(row, 0, safeGet(userEntry.getAddress()));
			usersTable.setText(row, 1, getName(userEntry));
			usersTable.setHTML(row, 2, constructMailTo(userEntry.getEmail()));
			usersTable.setText(row, 3, safeGet(userEntry.getPhone()));
			if (presenter.getUserRole() > UserRole.NONE && !isNullOrEmpty(userEntry.getEmail())) {
				usersTable.setWidget(row, 4, createEmailCheckBox(userEntry.getEmail()));
			}
		}
		
		userEdit.setVisible(presenter.getUserRole() > UserRole.NONE);
	}
	
	@UiHandler("userEdit")
	void onUserEditClick(ClickEvent event) {
		placeController.goTo(new UserEditPlace());
	}
	
	private void updateEmailSelections() {
		Set<String> filter = new HashSet<String>(emailSelections);
		StringBuilder stringBuilder = new StringBuilder();
		for (String email : filter) {
			if (!isNullOrEmpty(email)) {
				if (stringBuilder.length() > 0) {
					stringBuilder.append(", ");
				}
				stringBuilder.append(email);
			}
		}
		
		if (stringBuilder.length() > 0) {
			emailSelectionsTable.addStyleName("tableList");
			emailSelectionsTable.getRowFormatter().addStyleName(0, "tableListHeader");
			emailSelectionsTable.setText(0, 0, "Send email til valgte beboere ved at marker og kopier nedestående til udklipsholderen og indesætte det i \"til\" feltet i en ny email.");
			emailSelectionsTable.setWidget(1, 0, createSelectedMailToTextArea(stringBuilder.toString()));
		} else {
			emailSelectionsTable.removeStyleName("tableList");
			emailSelectionsTable.getRowFormatter().removeStyleName(0, "tableListHeader");
			emailSelectionsTable.removeAllRows();
		}
	}
	
	private CheckBox createEmailCheckBox(String email) {
		CheckBox checkBox = new CheckBox();
		checkBox.addClickHandler(new EmailClickHandler(email));
		return checkBox;
	}
	
	private String constructMailTo(String email) {
		return email != null ? "<a href=\"mailto:" + email + "\">" + email + "</a>" : "";
	}
	
	private TextArea createSelectedMailToTextArea(String text) {
		TextArea textArea = new TextArea();
		textArea.setReadOnly(true);
		textArea.addStyleName("textArea-selected");
		textArea.setText(text);
		return textArea;
	}
	
	private String getName(UserEntry userEntry) {
		String name = safeGet(userEntry.getFirstname());
		String lastName = safeGet(userEntry.getLastname());
		if (lastName.length() > 0) {
			name = name + " " + lastName;
		}
		
		return name;
	}
	
	private String safeGet(String string) {
		return string != null ? string : "";
	}
	
	private boolean isNullOrEmpty(String string) {
		return string == null || "".equals(string);
	}
	
	private final class EmailClickHandler implements ClickHandler {

		private String email;
		
		public EmailClickHandler(String email) {
			this.email = email;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox)event.getSource()).getValue();
			if (checked) {
				emailSelections.add(email);
			} else {
				emailSelections.remove(email);
			}
			updateEmailSelections();
		}
	}
}
