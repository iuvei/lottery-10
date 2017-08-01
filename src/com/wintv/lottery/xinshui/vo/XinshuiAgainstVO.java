package com.wintv.lottery.xinshui.vo;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 心水对阵VO
 *
 */
public class XinshuiAgainstVO {
	private Long againstId;
	private String hostName;
	private Long hostId;
	private String guestName;
	private Long guestId;
	private String startTime;
	private String raceName;
	private Long raceSeason;
	private String raceSeasonName;//赛季
	private Long rounds;
	private String roundsName;
	private String status;
	private String asiaPei;
	private String bigSmall;
	private String type;
	private String selectType;
	private Long raceId;
	private String img;//主队徽路径
	private String guestImg;//客队徽路径
	private String deadline;//心水发布截至时间
	private String phase;//期次
	private Long hostWin;
	private Long guestWin;
	private Long bigBall;
	private Long smallBall;
	private String ensureMoney;//缴纳的保证金
	private String price;//心水价格
	
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
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
	public String getRaceSeasonName() {
		return raceSeasonName;
	}
	public void setRaceSeasonName(String raceSeasonName) {
		this.raceSeasonName = raceSeasonName;
	}
	public Long getRounds() {
		return rounds;
	}
	public void setRounds(Long rounds) {
		this.rounds = rounds;
	}
	public String getRoundsName() {
		return roundsName;
	}
	public void setRoundsName(String roundsName) {
		this.roundsName = roundsName;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Long getHostWin() {
		return hostWin;
	}
	public void setHostWin(Long hostWin) {
		this.hostWin = hostWin;
	}
	public Long getGuestWin() {
		return guestWin;
	}
	public void setGuestWin(Long guestWin) {
		this.guestWin = guestWin;
	}
	public Long getBigBall() {
		return bigBall;
	}
	public void setBigBall(Long bigBall) {
		this.bigBall = bigBall;
	}
	public Long getSmallBall() {
		return smallBall;
	}
	public void setSmallBall(Long smallBall) {
		this.smallBall = smallBall;
	}
	public String getGuestImg() {
		return guestImg;
	}
	public void setGuestImg(String guestImg) {
		this.guestImg = guestImg;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getEnsureMoney() {
		return ensureMoney;
	}
	public void setEnsureMoney(String ensureMoney) {
		this.ensureMoney = ensureMoney;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
