package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TotalGoalSchedule implements Serializable {

	 

	private Long tgsId;
	private Date startTime;
	private String hostTeam;
	private String guestTeam;
	private String avgIndex;
	private Long hostGoal;
	private Long guestGoal;

	 
	public TotalGoalSchedule() {
	}

	 
	public TotalGoalSchedule(Date startTime, String hostTeam,
			String guestTeam, String avgIndex, Long hostGoal, Long guestGoal) {
		this.startTime = startTime;
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.avgIndex = avgIndex;
		this.hostGoal = hostGoal;
		this.guestGoal = guestGoal;
	}

	 

	public Long getTgsId() {
		return this.tgsId;
	}

	public void setTgsId(Long tgsId) {
		this.tgsId = tgsId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	public String getAvgIndex() {
		return this.avgIndex;
	}

	public void setAvgIndex(String avgIndex) {
		this.avgIndex = avgIndex;
	}

	public Long getHostGoal() {
		return this.hostGoal;
	}

	public void setHostGoal(Long hostGoal) {
		this.hostGoal = hostGoal;
	}

	public Long getGuestGoal() {
		return this.guestGoal;
	}

	public void setGuestGoal(Long guestGoal) {
		this.guestGoal = guestGoal;
	}

}