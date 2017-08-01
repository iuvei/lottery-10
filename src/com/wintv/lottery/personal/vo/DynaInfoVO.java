package com.wintv.lottery.personal.vo;

public class DynaInfoVO {
	private String username;//用户名
	private Long  sponsorBetUserId;//发起人用户ID
	private String sponsorUsername;//发起人用户名
	private String betCategory;//彩种
	private String phaseNo;//期次号码
	private String allMoney;//方案总金额
	private String betTime;//投注时间
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSponsorUsername() {
		return sponsorUsername;
	}
	public void setSponsorUsername(String sponsorUsername) {
		this.sponsorUsername = sponsorUsername;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}
	public String getBetTime() {
		return betTime;
	}
	public void setBetTime(String betTime) {
		this.betTime = betTime;
	}
	public Long getSponsorBetUserId() {
		return sponsorBetUserId;
	}
	public void setSponsorBetUserId(Long sponsorBetUserId) {
		this.sponsorBetUserId = sponsorBetUserId;
	}
	

}
