package com.wintv.lottery.admin.phase.vo;

import java.util.Date;

@Deprecated
public class LotteryAgainstVo {
	private Long againstId;// 主键
	private String hostName;//主队名称
	private Long hostId;
	private String guestName;//客队名称
	private Long guestId;
	private Date startTime;//开赛时间
	private String raceName;
	private Long raceSeason;
	private Long rounds;
	private String status;
	private String asiaPei;
	private String bigSmall;
	private String type;
	private Long raceId;
	private Long areaId;
	private Long countryId;
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public Long getGuestId() {
		return guestId;
	}
	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public Long getRaceSeason() {
		return raceSeason;
	}
	public void setRaceSeason(Long raceSeason) {
		this.raceSeason = raceSeason;
	}
	public Long getRounds() {
		return rounds;
	}
	public void setRounds(Long rounds) {
		this.rounds = rounds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAsiaPei() {
		return asiaPei;
	}
	public void setAsiaPei(String asiaPei) {
		this.asiaPei = asiaPei;
	}
	public String getBigSmall() {
		return bigSmall;
	}
	public void setBigSmall(String bigSmall) {
		this.bigSmall = bigSmall;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getRaceId() {
		return raceId;
	}
	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
}
