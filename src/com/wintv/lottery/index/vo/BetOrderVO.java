package com.wintv.lottery.index.vo;

import java.io.Serializable;
@SuppressWarnings("serial")
public class BetOrderVO  implements Serializable{
	private long betId;
	private String betUsername;
	private long betMilitary;
	private String allMoney;
	private long surplusCopys;
	private String progress;
	private String endTime;//截止时间
	private String betCategory;//彩种
	private String betCategoryZH;//彩种中文
	private String phaseNo;//期号
	private String bonus;//中奖金额
	private long phaseId;
	private String planCode;
	private String singleMoney;
	private String floorPercentage;
	public long getBetId() {
		return betId;
	}
	public void setBetId(long betId) {
		this.betId = betId;
	}
	public String getBetUsername() {
		return betUsername;
	}
	public void setBetUsername(String betUsername) {
		this.betUsername = betUsername;
	}
	public long getBetMilitary() {
		return betMilitary;
	}
	public void setBetMilitary(long betMilitary) {
		this.betMilitary = betMilitary;
	}
	public String getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}
	public long getSurplusCopys() {
		return surplusCopys;
	}
	public void setSurplusCopys(long surplusCopys) {
		this.surplusCopys = surplusCopys;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getBetCategoryZH() {
		return betCategoryZH;
	}
	public void setBetCategoryZH(String betCategoryZH) {
		this.betCategoryZH = betCategoryZH;
	}
	public long getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(long phaseId) {
		this.phaseId = phaseId;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getSingleMoney() {
		return singleMoney;
	}
	public void setSingleMoney(String singleMoney) {
		this.singleMoney = singleMoney;
	}
	public String getFloorPercentage() {
		return floorPercentage;
	}
	public void setFloorPercentage(String floorPercentage) {
		this.floorPercentage = floorPercentage;
	}
	

}
