package com.wintv.framework.pojo;

import java.util.Date;

/**
 * 足球单场赛程表(总进球数，让球胜平负等 均共用此赛程)
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class FootballSSchedule implements java.io.Serializable {

	 

	private Long scheduleId;//主键
	private Date startTime;//开赛时间
	private String hostTeam;//主队
	private String guestTeam;//客队
	private Long hostGoal;//主队进球数
	private Long guestGoal;//客队进球数
	private java.math.BigDecimal avgIndex;//平均指数

	 
	public FootballSSchedule() {
	}

	 
	public FootballSSchedule(Date startTime, String hostTeam,
			String guestTeam, Long hostGoal, Long guestGoal, java.math.BigDecimal avgIndex) {
		this.startTime = startTime;
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.hostGoal = hostGoal;
		this.guestGoal = guestGoal;
		this.avgIndex = avgIndex;
	}

	 

	public Long getScheduleId() {
		return this.scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
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

	public java.math.BigDecimal getAvgIndex() {
		return this.avgIndex;
	}

	public void setAvgIndex(java.math.BigDecimal avgIndex) {
		this.avgIndex = avgIndex;
	}

}