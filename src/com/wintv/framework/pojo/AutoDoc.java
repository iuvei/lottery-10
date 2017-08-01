package com.wintv.framework.pojo;

/**
 * 
 * 自动跟单
 *
 */
@SuppressWarnings("serial")
public class AutoDoc implements java.io.Serializable {

	 

	private Long adId;
	private Long sponsorUserId;
	private String phase;
	private String lotteryType;
	private Long militaryScore;
	private Double autoMoney;
	private Double allMoney;
	private Long docUserId;

	 
	public AutoDoc() {
	}

	 
	public AutoDoc(Long sponsorUserId, String phase,
			String lotteryType, Long militaryScore, Double autoMoney,
			Double allMoney, Long docUserId) {
		this.sponsorUserId = sponsorUserId;
		this.phase = phase;
		this.lotteryType = lotteryType;
		this.militaryScore = militaryScore;
		this.autoMoney = autoMoney;
		this.allMoney = allMoney;
		this.docUserId = docUserId;
	}

	 

	public Long getAdId() {
		return this.adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Long getSponsorUserId() {
		return this.sponsorUserId;
	}

	public void setSponsorUserId(Long sponsorUserId) {
		this.sponsorUserId = sponsorUserId;
	}

	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getLotteryType() {
		return this.lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getMilitaryScore() {
		return this.militaryScore;
	}

	public void setMilitaryScore(Long militaryScore) {
		this.militaryScore = militaryScore;
	}

	public Double getAutoMoney() {
		return this.autoMoney;
	}

	public void setAutoMoney(Double autoMoney) {
		this.autoMoney = autoMoney;
	}

	public Double getAllMoney() {
		return this.allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	public Long getDocUserId() {
		return this.docUserId;
	}

	public void setDocUserId(Long docUserId) {
		this.docUserId = docUserId;
	}

}