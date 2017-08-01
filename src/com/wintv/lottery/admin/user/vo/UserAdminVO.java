package com.wintv.lottery.admin.user.vo;

import java.math.BigDecimal;
/**
 * 用户存储后台用户管理列表用户信息
 * @author hikin
 *
 */
public class UserAdminVO {
	private Long userId;//用户ID
	private String userName;//用户名
	private String userStatus;//用户状态
	private String userMoneyStatus;//资金状态
	private String userGrade;//用户等级
	private String userHonor;//用户头衔
	
	private BigDecimal accountBalance;//账户余额
	private BigDecimal totalWinPrize;//总中奖金
	
	private BigDecimal totalBet;//总投注额
	private String userMobile;//会员手机

	private BigDecimal heartWaterScore;//心水战绩
	private BigDecimal forecastPeriod;//预测场次
	
	private BigDecimal saleHeartWater;//销售心水
	private BigDecimal buyHeartWater;//购心水额	
	
	private BigDecimal togetherBuysScore ;//合买战绩
	private BigDecimal releaseTogetherBuys;//发起合买
	private BigDecimal togetherBuysSales;//合买销售
	private BigDecimal arenaScore;//擂台战绩
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getUserMoneyStatus() {
		return userMoneyStatus;
	}
	public void setUserMoneyStatus(String userMoneyStatus) {
		this.userMoneyStatus = userMoneyStatus;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public String getUserHonor() {
		return userHonor;
	}
	public void setUserHonor(String userHonor) {
		this.userHonor = userHonor;
	}
	public BigDecimal getTotalWinPrize() {
		return totalWinPrize;
	}
	public void setTotalWinPrize(BigDecimal totalWinPrize) {
		this.totalWinPrize = totalWinPrize;
	}
	public BigDecimal getTotalBet() {
		return totalBet;
	}
	public void setTotalBet(BigDecimal totalBet) {
		this.totalBet = totalBet;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public BigDecimal getSaleHeartWater() {
		return saleHeartWater;
	}
	public void setSaleHeartWater(BigDecimal saleHeartWater) {
		this.saleHeartWater = saleHeartWater;
	}
	public BigDecimal getBuyHeartWater() {
		return buyHeartWater;
	}
	public void setBuyHeartWater(BigDecimal buyHeartWater) {
		this.buyHeartWater = buyHeartWater;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public BigDecimal getHeartWaterScore() {
		return heartWaterScore;
	}
	public void setHeartWaterScore(BigDecimal heartWaterScore) {
		this.heartWaterScore = heartWaterScore;
	}
	public BigDecimal getTogetherBuysScore() {
		return togetherBuysScore;
	}

	public BigDecimal getTogetherBuysSales() {
		return togetherBuysSales;
	}
	public void setTogetherBuysSales(BigDecimal togetherBuysSales) {
		this.togetherBuysSales = togetherBuysSales;
	}
	public BigDecimal getArenaScore() {
		return arenaScore;
	}
	public void setArenaScore(BigDecimal arenaScore) {
		this.arenaScore = arenaScore;
	}
	public BigDecimal getForecastPeriod() {
		return forecastPeriod;
	}
	public void setForecastPeriod(BigDecimal forecastPeriod) {
		this.forecastPeriod = forecastPeriod;
	}
	public BigDecimal getReleaseTogetherBuys() {
		return releaseTogetherBuys;
	}
	public void setReleaseTogetherBuys(BigDecimal releaseTogetherBuys) {
		this.releaseTogetherBuys = releaseTogetherBuys;
	}
	public void setTogetherBuysScore(BigDecimal togetherBuysScore) {
		this.togetherBuysScore = togetherBuysScore;
	}
    
}
