package com.wintv.lottery.attention.vo;

import java.math.BigDecimal;

public class AttentionPlanVO {
	private Long id;//
	private String lotteryCategory;
	private String lotteryType;//
	private String phaseNo;//期次号
	private String sponsorUsername;//发起人用户名
	private String stars;//用户投注级别
	private BigDecimal allMoney;//方案总金额
	private String progress;//进度
	private String betCategoty;//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场 62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
	public String getLotteryCategory() {
		return lotteryCategory;
	}
	public void setLotteryCategory(String lotteryCategory) {
		this.lotteryCategory = lotteryCategory;
	}
	public String getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getSponsorUsername() {
		return sponsorUsername;
	}
	public void setSponsorUsername(String sponsorUsername) {
		this.sponsorUsername = sponsorUsername;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBetCategoty() {
		return betCategoty;
	}
	public void setBetCategoty(String betCategoty) {
		this.betCategoty = betCategoty;
	}

}
