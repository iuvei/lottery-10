package com.wintv.framework.pojo;

import java.lang.Long;
import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")
public class HistoryRace implements Serializable {

	private Long hrId;
	private String raceType;//'1' :联赛    '2':杯赛     '3':其他
	private Date  startTime;//比赛时间
	private Long hostTeam;//主队,关联T_TEAM表的主键
	private Long guestTeam;//客队,关联T_TEAM表的主键
	private Long point;//积分
	private String halfScore;//半场比分
	private String concedePoint;//让球
	private String roadBed;//盘路
	private String bigOrSmallBall;//大小球
	private String raceName;//比赛名称
	
	public HistoryRace(Long hrId, String raceType, Date startTime,
			Long hostTeam, Long guestTeam, Long point, String halfScore,
			String concedePoint, String roadBed, String bigOrSmallBall,
			String raceName) {
	    this.hrId = hrId;
		this.raceType = raceType;
		this.startTime = startTime;
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.point = point;
		this.halfScore = halfScore;
		this.concedePoint = concedePoint;
		this.roadBed = roadBed;
		this.bigOrSmallBall = bigOrSmallBall;
		this.raceName = raceName;
	}
	public HistoryRace() {
	
	}
	public Long getHrId() {
		return hrId;
	}
	public void setHrId(Long hrId) {
		this.hrId = hrId;
	}
	public String getRaceType() {
		return raceType;
	}
	public void setRaceType(String raceType) {
		this.raceType = raceType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Long getHostTeam() {
		return hostTeam;
	}
	public void setHostTeam(Long hostTeam) {
		this.hostTeam = hostTeam;
	}
	public Long getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(Long guestTeam) {
		this.guestTeam = guestTeam;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(Long point) {
		this.point = point;
	}
	public String getHalfScore() {
		return halfScore;
	}
	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}
	public String getConcedePoint() {
		return concedePoint;
	}
	public void setConcedePoint(String concedePoint) {
		this.concedePoint = concedePoint;
	}
	public String getRoadBed() {
		return roadBed;
	}
	public void setRoadBed(String roadBed) {
		this.roadBed = roadBed;
	}
	public String getBigOrSmallBall() {
		return bigOrSmallBall;
	}
	public void setBigOrSmallBall(String bigOrSmallBall) {
		this.bigOrSmallBall = bigOrSmallBall;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	
}