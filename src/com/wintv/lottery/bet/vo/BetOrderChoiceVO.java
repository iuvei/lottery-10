package com.wintv.lottery.bet.vo;

public class BetOrderChoiceVO {
	private long choiceId;
	private long changci;
	private String hostName;
	private String guestName;
	private String concede;
	private String betPlan;//您的投注
	private String dingdan;
	public long getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(long choiceId) {
		this.choiceId = choiceId;
	}
	public long getChangci() {
		return changci;
	}
	public void setChangci(long changci) {
		this.changci = changci;
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
	public String getConcede() {
		return concede;
	}
	public void setConcede(String concede) {
		this.concede = concede;
	}
	public String getBetPlan() {
		return betPlan;
	}
	public void setBetPlan(String betPlan) {
		this.betPlan = betPlan;
	}
	public String getDingdan() {
		return dingdan;
	}
	public void setDingdan(String dingdan) {
		this.dingdan = dingdan;
	}

}
