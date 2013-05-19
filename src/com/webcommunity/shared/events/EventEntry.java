package com.webcommunity.shared.events;

import java.io.Serializable;
import java.util.Date;

public class EventEntry implements Serializable {

	private static final long serialVersionUID = 8671814916910663709L;

	private String key;
	private Date date;
	private String description;
	
	public EventEntry() {
	}

	public EventEntry(Date date, String description) {
		this(null, date, description);
	}

	public EventEntry(String key, Date date, String description) {
		this.key = key;
		this.date = date;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
