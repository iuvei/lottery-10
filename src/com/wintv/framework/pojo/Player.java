package com.wintv.framework.pojo;

import java.io.Serializable;
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long playerId;//主键
	private String name;//球员姓名
	private Long age;//年龄
	private Long height;//身高
	private Long countryId;//国籍,关联T_COUNTERY表

	
	public Player() {
	}

	 
	public Player(String name, Long age, Long height, Long countryId) {
		this.name = name;
		this.age = age;
		this.height = height;
		this.countryId = countryId;
	}

	 

	public Long getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return this.age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getHeight() {
		return this.height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

}