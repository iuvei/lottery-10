package com.wintv.lottery.pay.vo;

import java.math.BigDecimal;
/**
 * 后台充值系统
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public class ChargeLogVo {
	private Long chargeId;//充值记录ID
	private String orderNO;//订单号
	private Long userId;//用户ID
	private String userName;//用户名称
	private String userGrade;//用户等级
	private BigDecimal chargeMoney;//充值金额
	private String chargeWay;//充值方式

	private String chargeBank;//充值银行
	private String chargeBankCode;//充值银行代码
	private String chargeStatus;//充值状态
	private String chargeTime;//充值时间
	private String chargeIP;//充值Ip
	
	public Long getChargeId() {
		return chargeId;
	}
	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public BigDecimal getChargeMoney() {
		return chargeMoney;
	}
	public void setChargeMoney(BigDecimal chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	public String getChargeWay() {
		return chargeWay;
	}
	public void setChargeWay(String chargeWay) {
		this.chargeWay = chargeWay;
	}
	public String getChargeBank() {
		return chargeBank;
	}
	public void setChargeBank(String chargeBank) {
		this.chargeBank = chargeBank;
	}
	public String getChargeBankCode() {
		return chargeBankCode;
	}
	public void setChargeBankCode(String chargeBankCode) {
		this.chargeBankCode = chargeBankCode;
	}
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public String getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getChargeIP() {
		return chargeIP;
	}
	public void setChargeIP(String chargeIP) {
		this.chargeIP = chargeIP;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
}
