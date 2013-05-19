package com.webcommunity.shared.users;

import java.io.Serializable;
import java.util.Date;

public class UserEntry implements Serializable {

	private static final long serialVersionUID = -3273724597933368442L;

	public static final int MINIMUM_USER_AND_PASS_LENGTH = 2;
	
	private String identifier;
	private String sessionId;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String address;
	private String email;
	private String phone;
	private Date birthdate;
	private Long userRole;
	private Boolean subscribe;
	
	
	public UserEntry() {
	}

	public UserEntry(String identifier, String sessionId, String username, String password, String firstname, String lastname, String address, String email, String phone, Date birthdate, Long userRole, Boolean subscribe) {
		this.identifier = identifier;
		this.sessionId = sessionId;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.birthdate = birthdate;
		this.userRole = userRole;
		this.subscribe = subscribe;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Long getUserRole() {
		return userRole;
	}

	public void setUserRole(Long userRole) {
		this.userRole = userRole;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}
	
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();
		if (firstname != null && !firstname.isEmpty()) {
			sb.append(firstname);
		}
		
		if (lastname != null && !lastname.isEmpty()) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(lastname);
		}
		
		if (sb.length() == 0 && username != null) {
			sb.append(username);
		}
	
		return (sb.length() > 0) ? sb.toString() : null;
	}
}
