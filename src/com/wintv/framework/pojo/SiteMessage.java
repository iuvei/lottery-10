package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;


public class SiteMessage implements Serializable {

	// Fields

	private Long messageId;
	private String title;
	private String content;

	// Constructors

	/** default constructor */
	public SiteMessage() {
	}

	/** full constructor */
	public SiteMessage(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// Property accessors

	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}