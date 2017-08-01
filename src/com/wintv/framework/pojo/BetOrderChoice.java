package com.wintv.framework.pojo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BetOrderChoice implements Serializable {
	private Long choiceId;
	private Long againstId;//对阵ID
	private Long betOrderId;//投注订单ID
	private String betPlan;//用户投注结果
	private String danCode;//胆码
	private Long changci;//场次 由体彩中心提供
	private String hostName;//主队
	private String guestName;//客队
	private String concede;//让球
	private String score;//全场比分
	private String sp;//赔率
	public Long getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	public Long getBetOrderId() {
		return betOrderId;
	}
	public void setBetOrderId(Long betOrderId) {
		this.betOrderId = betOrderId;
	}
	public String getBetPlan() {
		return betPlan;
	}
	public void setBetPlan(String betPlan) {
		this.betPlan = betPlan;
	}
	public String getDanCode() {
		return danCode;
	}
	public void setDanCode(String danCode) {
		this.danCode = danCode;
	}
	public Long getChangci() {
		return changci;
	}
	public void setChangci(Long changci) {
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	

}