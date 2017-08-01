package com.wintv.lottery.admin.user.vo;
import java.math.BigDecimal;
public class UserAccountInfoVO {
	private Long userId;//用户ID
	private BigDecimal currentMoney;//当前账户余额
	private BigDecimal frozenMoney;//当前冻结资金
	private BigDecimal totalDrawMoney;//总取款额
	
	private BigDecimal totalPayMoney;//总充值金额
	private BigDecimal totalBetMoney;//总投注金额
	private BigDecimal totalWinMoney;//总中奖金额
	
	private BigDecimal currentGold;//当前可用彩金
	private BigDecimal totalGold;//总彩金
	private BigDecimal totalUseGold;//总使用彩金
	
	private Integer currentPoint;//当前可用积分
	private Integer totalPoint;//总积分
	private Integer totalUsePoint;//总使用积分
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getCurrentMoney() {
		return currentMoney;
	}
	public void setCurrentMoney(BigDecimal currentMoney) {
		this.currentMoney = currentMoney;
	}
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public BigDecimal getTotalDrawMoney() {
		return totalDrawMoney;
	}
	public void setTotalDrawMoney(BigDecimal totalDrawMoney) {
		this.totalDrawMoney = totalDrawMoney;
	}
	public BigDecimal getTotalPayMoney() {
		return totalPayMoney;
	}
	public void setTotalPayMoney(BigDecimal totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}
	public BigDecimal getTotalBetMoney() {
		return totalBetMoney;
	}
	public void setTotalBetMoney(BigDecimal totalBetMoney) {
		this.totalBetMoney = totalBetMoney;
	}
	public BigDecimal getTotalWinMoney() {
		return totalWinMoney;
	}
	public void setTotalWinMoney(BigDecimal totalWinMoney) {
		this.totalWinMoney = totalWinMoney;
	}
	public BigDecimal getCurrentGold() {
		return currentGold;
	}
	public void setCurrentGold(BigDecimal currentGold) {
		this.currentGold = currentGold;
	}
	public BigDecimal getTotalGold() {
		return totalGold;
	}
	public void setTotalGold(BigDecimal totalGold) {
		this.totalGold = totalGold;
	}
	public BigDecimal getTotalUseGold() {
		return totalUseGold;
	}
	public void setTotalUseGold(BigDecimal totalUseGold) {
		this.totalUseGold = totalUseGold;
	}
	public Integer getCurrentPoint() {
		return currentPoint;
	}
	public void setCurrentPoint(Integer currentPoint) {
		this.currentPoint = currentPoint;
	}
	public Integer getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}
	public Integer getTotalUsePoint() {
		return totalUsePoint;
	}
	public void setTotalUsePoint(Integer totalUsePoint) {
		this.totalUsePoint = totalUsePoint;
	}
	
}
