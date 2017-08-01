package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;


public class PlayerTeam implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long ptId;
	private Long playerId;
	private Long teamId;
	private Date startTime;//入队起始时间
	private Date endTime;//入队结束时间
	public Long getPtId() {
		return ptId;
	}
	public void setPtId(Long ptId) {
		this.ptId = ptId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


}