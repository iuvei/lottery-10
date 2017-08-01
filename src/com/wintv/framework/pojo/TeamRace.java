package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TeamRace implements Serializable {

	

	private Long id;
	private Long teamId;
	private String teamName;
	private Long raceId;
	private Date raceTime;
	private Long raceSeasonId;
	private String raceSeasonName;
	private Long countryId;
	private String countryName;
	private Long areaId;
	private String areaName;
	private String type;

	
	public TeamRace() {
	}

	
	public TeamRace(Long teamId,String teamName, Long raceId, Date raceTime,
			Long raceSeasonId, String raceSeasonName, Long countryId,
			String countryName, Long areaId, String areaName, String type) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.raceId = raceId;
		this.raceTime = raceTime;
		this.raceSeasonId = raceSeasonId;
		this.raceSeasonName = raceSeasonName;
		this.countryId = countryId;
		this.countryName = countryName;
		this.areaId = areaId;
		this.areaName = areaName;
		this.type = type;
	}

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public Long getRaceId() {
		return this.raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}

	public Date getRaceTime() {
		return this.raceTime;
	}

	public void setRaceTime(Date raceTime) {
		this.raceTime = raceTime;
	}

	public Long getRaceSeasonId() {
		return this.raceSeasonId;
	}

	public void setRaceSeasonId(Long raceSeasonId) {
		this.raceSeasonId = raceSeasonId;
	}

	public String getRaceSeasonName() {
		return this.raceSeasonName;
	}

	public void setRaceSeasonName(String raceSeasonName) {
		this.raceSeasonName = raceSeasonName;
	}

	public Long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}