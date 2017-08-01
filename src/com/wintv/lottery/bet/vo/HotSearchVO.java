package com.wintv.lottery.bet.vo;

/**
 * 热门发起订单(合买) 2010-03-26 13:39
 **/
public class HotSearchVO {
	private long  hotId;//主键
	private long userId;//热门发起人用户ID
	private String username;//热门发起人用户名
	private long betId;//订单ID
	private String betCategory;//彩种
	private String phaseNo;//期次号
	public long getHotId() {
		return hotId;
	}
	public void setHotId(long hotId) {
		this.hotId = hotId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getBetId() {
		return betId;
	}
	public void setBetId(long betId) {
		this.betId = betId;
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

}
