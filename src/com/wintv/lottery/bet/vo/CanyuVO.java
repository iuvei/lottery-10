package com.wintv.lottery.bet.vo;

/**参与合买**/
public class CanyuVO {
	private String betUsername;
	private String subscribeMoney;
	private String bonus;
	private String  betTime;
	private int subscribeCopys;
	private String autoRatio;//自己认购百分比
	public String getBetUsername() {
		return betUsername;
	}
	public void setBetUsername(String betUsername) {
		this.betUsername = betUsername;
	}
	public String getSubscribeMoney() {
		return subscribeMoney;
	}
	public void setSubscribeMoney(String subscribeMoney) {
		this.subscribeMoney = subscribeMoney;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getBetTime() {
		return betTime;
	}
	public void setBetTime(String betTime) {
		this.betTime = betTime;
	}
	public int getSubscribeCopys() {
		return subscribeCopys;
	}
	public void setSubscribeCopys(int subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}
	public String getAutoRatio() {
		return autoRatio;
	}
	public void setAutoRatio(String autoRatio) {
		this.autoRatio = autoRatio;
	}
	

}
