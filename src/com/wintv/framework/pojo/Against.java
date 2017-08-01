package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 总的对阵信息(所有的对阵信息都保存在表:T_AGAINST里)
 *
 *
 */
@SuppressWarnings({"serial","unchecked"})
public class Against implements Serializable {

	private Long againstId;// 主键
	private String hostName;//主队名称
	private Long hostId;
	private String guestName;//客队名称
	private Long guestId;
	private Date startTime;//开赛时间
	private String raceName;
	private Long raceSeasonId;//赛季
	private String raceSeasonName;
	private Long rounds;//轮次
	private String roundName;
	private String status;
	private String asiaPei;
	private String bigSmall;
	private String type;//赛事类型'1':联赛   '2':杯赛   '3':其他
	private Long raceId;
	private Long areaId;
	private Long countryId;
	private String scoreA;//上半场比分
	private String scoreB;//下半场比分
	private String score;//全场比分
	private String raceResult;//赛果
	private String concede;//让球
	private String bgColor;//颜色
	private String concedeResult;//让球赛果
	public String getConcedeResult() {
		return concedeResult;
	}
	public void setConcedeResult(String concedeResult) {
		this.concedeResult = concedeResult;
	}
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	private List  againstList;
	public Against(Long againstId,String hostName,String guestName){
		this.againstId=againstId;
		this.hostName=hostName;
		this.guestName=guestName;
	}
	public Against(){}
	
	
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
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}


	public List getAgainstList() {
		return againstList;
	}


	public void setAgainstList(List againstList) {
		this.againstList = againstList;
	}
	
	public String getRaceResult() {
		return raceResult;
	}
	public void setRaceResult(String raceResult) {
		this.raceResult = raceResult;
	}
	public String getConcede() {
		return concede;
	}
	public void setConcede(String concede) {
		this.concede = concede;
	}
	public Long getRaceSeasonId() {
		return raceSeasonId;
	}
	public void setRaceSeasonId(Long raceSeasonId) {
		this.raceSeasonId = raceSeasonId;
	}
	public String getRaceSeasonName() {
		return raceSeasonName;
	}
	public void setRaceSeasonName(String raceSeasonName) {
		this.raceSeasonName = raceSeasonName;
	}
	public String getRoundName() {
		return roundName;
	}
	public void setRoundName(String roundName) {
		this.roundName = roundName;
	}
	public String getScoreA() {
		return scoreA;
	}
	public void setScoreA(String scoreA) {
		this.scoreA = scoreA;
	}
	public String getScoreB() {
		return scoreB;
	}
	public void setScoreB(String scoreB) {
		this.scoreB = scoreB;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}


}