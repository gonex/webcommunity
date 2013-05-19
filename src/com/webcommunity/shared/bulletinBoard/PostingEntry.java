package com.webcommunity.shared.bulletinBoard;

import java.io.Serializable;
import java.util.Date;

public class PostingEntry implements Serializable {

	private static final long serialVersionUID = -8614151904721460765L;

	private String key;
	private Date date;
	private String user;
	private String title;
	private String content;
	private Boolean isLast;
	
	
	public PostingEntry() {
	}
	
	public PostingEntry(String title, String content) {
		this(null, null, null, title, content, null);
	}

	public PostingEntry(String key, Date date, String user, String title, String content, Boolean isLast) {
		this.key = key;
		this.date = date;
		this.user = user;
		this.title = title;
		this.content = content;
		this.isLast = isLast;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsLast() {
		return isLast;
	}

	public void setIsLast(Boolean isLast) {
		this.isLast = isLast;
	}
}
