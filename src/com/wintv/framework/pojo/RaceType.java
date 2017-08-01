package com.wintv.framework.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RaceType implements Serializable {

	private Long raceId;
	private String name;
	private String type;
	public Long getRaceId() {
		return raceId;
	}
	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}