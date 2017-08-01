package com.wintv.framework.pojo;

import java.io.Serializable;


public class Content implements Serializable {

	 

	private Long contentId;
	private Long articleId;
	private String content;
	private Long page;

	 
	public Content() {
	}

	 
	public Content(Long articleId, String content, Long page) {
		this.articleId = articleId;
		this.content = content;
		this.page = page;
	}

	 

	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getArticleId() {
		return this.articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPage() {
		return this.page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

}