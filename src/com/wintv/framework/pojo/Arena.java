package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


public class Arena implements Serializable {

	// Fields

	private Long arenaId;
	private String hostTeam;
	private String guestTeam;
	private Date raceTime;
	private String avgIndex;
	private String phase;

	// Constructors

	/** default constructor */
	public Arena() {
	}

	/** minimal constructor */
	public Arena(String hostTeam, String guestTeam, Date raceTime,
			String phase) {
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.raceTime = raceTime;
		this.phase = phase;
	}

	/** full constructor */
	public Arena(String hostTeam, String guestTeam, Date raceTime,
			String avgIndex, String phase) {
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.raceTime = raceTime;
		this.avgIndex = avgIndex;
		this.phase = phase;
	}

	// Property accessors

	public Long getArenaId() {
		return this.arenaId;
	}

	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}

	public String getHostTeam() {
		return this.hostTeam;
	}

	public void setHostTeam(String hostTeam) {
		this.hostTeam = hostTeam;
	}

	public String getGuestTeam() {
		return this.guestTeam;
	}

	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}

	public Date getRaceTime() {
		return this.raceTime;
	}

	public void setRaceTime(Date raceTime) {
		this.raceTime = raceTime;
	}

	public String getAvgIndex() {
		return this.avgIndex;
	}

	public void setAvgIndex(String avgIndex) {
		this.avgIndex = avgIndex;
	}

	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

}