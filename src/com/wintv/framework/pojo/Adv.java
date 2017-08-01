package com.wintv.framework.pojo;

import java.lang.Long;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Adv implements Serializable {

	private Long advId;
	private String title;
	private String content;
	private String attach;

	public Adv() {
	}

	
	public Adv(String title, String content, String attach) {
		this.title = title;
		this.content = content;
		this.attach = attach;
	}



	public Long getAdvId() {
		return this.advId;
	}

	public void setAdvId(Long advId) {
		this.advId = advId;
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

	public String getAttach() {
		return this.attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}