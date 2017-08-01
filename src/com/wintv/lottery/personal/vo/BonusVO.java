package com.wintv.lottery.personal.vo;


public class BonusVO {
	private String phaseNo;
    private String betCategory;
	private String money;
	private String username;
	private long userId;
	private String allowDZ="0";//是否允许定制
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getAllowDZ() {
		return allowDZ;
	}
	public void setAllowDZ(String allowDZ) {
		this.allowDZ = allowDZ;
	}
}
