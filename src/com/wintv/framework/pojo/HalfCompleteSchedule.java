package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 半全场赛程
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class HalfCompleteSchedule implements Serializable {

	

	private Long hcsId;
	private String raceType;
	private Date startTime;
	private Date endTime;
	private String hostTeam;
	private String guestTeam;
	private String score;
	private String data;

	
	public HalfCompleteSchedule() {
	}

	
	public HalfCompleteSchedule(String raceType, Date startTime,
			Date endTime, String hostTeam, String guestTeam, String score,
			String data) {
		this.raceType = raceType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.score = score;
		this.data = data;
	}

	

	public Long getHcsId() {
		return this.hcsId;
	}

	public void setHcsId(Long hcsId) {
		this.hcsId = hcsId;
	}

	public String getRaceType() {
		return this.raceType;
	}

	public void setRaceType(String raceType) {
		this.raceType = raceType;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

}