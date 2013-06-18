package com.webcommunity.client.page.useredit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.webcommunity.client.error.MessageDialog;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.users.UserEntry;

public class UserEditViewImpl extends Composite implements UserEditView {

	interface UserEditViewImplUiBinder extends UiBinder<Widget, UserEditViewImpl> {
	}

	private static UserEditViewImplUiBinder uiBinder = GWT.create(UserEditViewImplUiBinder.class);

	@UiField FlexTable editUserTable;
	@UiField Button updateButton;
	@UiField Button createButton;

	private TextBox username;
	private PasswordTextBox password;
	private PasswordTextBox confirmPassword;
	private TextBox firstname;
	private TextBox lastname;
	private TextBox address;
	private TextBox email;
	private TextBox phone;
	private DateBox birthdate;
	
	private Presenter presenter;
	private UserEntry userEntry;

	
	public UserEditViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.presenter.getUser();
	}

	@Override
	public void updateUser(UserEntry result) {
		userEntry = result;
		
		editUserTable.removeAllRows();

		editUserTable.addStyleName("tableList");
		editUserTable.getRowFormatter().addStyleName(0, "tableListHeader");
		
		editUserTable.setText(0, 0, "Felt");
		editUserTable.setText(0, 1, "Værdi");
		
		if (userEntry != null) {
			editUserTable.setText(1, 0, "Brugernavn");
			username = new TextBox();
			username.setMaxLength(50);
			username.setWidth("170px");
			username.setText(userEntry.getUsername());
			editUserTable.setWidget(1, 1, username);
	
			editUserTable.setText(2, 0, "Kodeord");
			password = new PasswordTextBox();
			password.setMaxLength(50);
			password.setWidth("170px");
			password.setText(userEntry.getPassword());
			editUserTable.setWidget(2, 1, password);
			
			editUserTable.setText(3, 0, "Bekræft kodeord");
			confirmPassword = new PasswordTextBox();
			confirmPassword.setMaxLength(50);
			confirmPassword.setWidth("170px");
			confirmPassword.setText(userEntry.getPassword());
			editUserTable.setWidget(3, 1, confirmPassword);

			editUserTable.setText(4, 0, "Fornavn");
			firstname = new TextBox();
			firstname.setMaxLength(50);
			firstname.setWidth("170px");
			firstname.setText(userEntry.getFirstname());
			editUserTable.setWidget(4, 1, firstname);
	
			editUserTable.setText(5, 0, "Efternavn");
			lastname = new TextBox();
			lastname.setMaxLength(50);
			lastname.setWidth("170px");
			lastname.setText(userEntry.getLastname());
			editUserTable.setWidget(5, 1, lastname);

			editUserTable.setText(6, 0, "Addresse");
			address = new TextBox();
			address.setMaxLength(50);
			address.setWidth("170px");
			address.setText(userEntry.getAddress());
			editUserTable.setWidget(6, 1, address);
			
			editUserTable.setText(7, 0, "Email");
			email = new TextBox();
			email.setMaxLength(50);
			email.setWidth("170px");
			email.setText(userEntry.getEmail());
			editUserTable.setWidget(7, 1, email);
	
			editUserTable.setText(8, 0, "Telefon");
			phone = new TextBox();
			phone.setMaxLength(50);
			phone.setWidth("170px");
			phone.setText(userEntry.getPhone());
			editUserTable.setWidget(8, 1, phone);
	
			editUserTable.setText(9, 0, "Fødselsdato");
			birthdate = new DateBox();
			birthdate.setWidth("170px");
			birthdate.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM-yyyy")));
			birthdate.setValue(userEntry.getBirthdate());
			editUserTable.setWidget(9, 1, birthdate);
		}
		
		createButton.setVisible(presenter.getUserRole() >= UserRole.ADMINISTRATOR);
	}

	@UiHandler("updateButton")
	protected void onUpdateButtonClick(ClickEvent e) {
		if (!validInput()) {
			return;
		}
		
		userEntry.setUsername(username.getText());
		userEntry.setPassword(password.getText());
		userEntry.setFirstname(firstname.getText());
		userEntry.setLastname(lastname.getText());
		userEntry.setAddress(address.getText());
		userEntry.setEmail(email.getText());
		userEntry.setPhone(phone.getText());
		userEntry.setBirthdate(birthdate.getValue());
		
		presenter.updateUser(userEntry);
	}

	@UiHandler("createButton")
	protected void onCreateButtonClick(ClickEvent e) {
		if (!validInput()) {
			return;
		}
		
		userEntry.setUsername(username.getText());
		userEntry.setPassword(password.getText());
		userEntry.setFirstname(firstname.getText());
		userEntry.setLastname(lastname.getText());
		userEntry.setAddress(address.getText());
		userEntry.setEmail(email.getText());
		userEntry.setPhone(phone.getText());
		userEntry.setBirthdate(birthdate.getValue());
		userEntry.setUserRole(UserRole.NORMAL);
		userEntry.setSubscribe(Boolean.TRUE);
		
		presenter.createUser(userEntry);
	}
	
	private boolean validInput() {
		if (username.getText().length() < UserEntry.MINIMUM_USER_AND_PASS_LENGTH) {
			MessageDialog.show("Ugyldig brugernavn", "Brugernavnet er for kort.");
			return false;
		}

		if (password.getText().length() < UserEntry.MINIMUM_USER_AND_PASS_LENGTH) {
			MessageDialog.show("Ugyldig kodeord", "Brugernavnet er for kort.");
			return false;
		}
		
		if (!password.getText().equals(confirmPassword.getText())) {
			MessageDialog.show("Forskellige kodeord", "Værdien i felterne kodeord og bekræft kodeord er forskellige.");
			return false;
		}
		
		if (birthdate.getTextBox().getText().length() > 0 && birthdate.getValue() == null) {
			MessageDialog.show("Ugyldig fødselsdato", "Fødselsdato indeholder ikke en gyldig dato.");
			return false;
		}
		
		return true;
	}
}
