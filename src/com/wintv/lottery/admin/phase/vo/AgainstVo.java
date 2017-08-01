package com.wintv.lottery.admin.phase.vo;


public class AgainstVo {
	private String hostName;//主队名称
	private String guestName;//客队名称
	private Long againstId;
	private String startTime;//开赛时间
	private String type;//赛事类型'1':联赛   '2':杯赛   '3':其他
	private String raceName;//赛事名称
	private String concede;//让球
	private String scoreA;
	private String scoreB;
	private String score;
	private Long isxinshui ;          // 该赛事是否属于薪水
	private Long raceId;//联赛ID
	private Long hostId;//主对ID
	private Long gusetsId;//客队ID
	private String bgColor;//颜色
	private int isAvaliable;//1  正常显示   2.表示该比赛已经结束
	private String changci;
	private String statPlan;//选号统计结果
	private String resultNo;//开奖号码
	public int getIsAvaliable() {
		return isAvaliable;
	}
	public void setIsAvaliable(int isAvaliable) {
		this.isAvaliable = isAvaliable;
	}
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public Long getRaceId() {
		return raceId;
	}
	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public Long getGusetsId() {
		return gusetsId;
	}
	public void setGusetsId(Long gusetsId) {
		this.gusetsId = gusetsId;
	}
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public String getConcede() {
		return concede;
	}
	public void setConcede(String concede) {
		this.concede = concede;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Long getIsxinshui() {
		return isxinshui;
	}
	public void setIsxinshui(Long isxinshui) {
		this.isxinshui = isxinshui;
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
	public String getChangci() {
		return changci;
	}
	public void setChangci(String changci) {
		this.changci = changci;
	}
	public String getStatPlan() {
		return statPlan;
	}
	public void setStatPlan(String statPlan) {
		this.statPlan = statPlan;
	}
	public String getResultNo() {
		return resultNo;
	}
	public void setResultNo(String resultNo) {
		this.resultNo = resultNo;
	}

}
