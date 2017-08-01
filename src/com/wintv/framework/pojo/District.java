package com.wintv.framework.pojo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class District implements Serializable {

 

	private Long id;
	private String name;
	private Long parentId;

	 
	public District() {
	}

	 
	public District(String name, Long parentId) {
		this.name = name;
		this.parentId = parentId;
	}

	 

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}