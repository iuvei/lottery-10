package com.wintv.lottery.pay.vo;

import java.math.BigDecimal;
/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public class WithdrawLogVo {
	private Long drawLogId;//取现记录ID
	private String orderNO;//订单号
	private Long userId;//用户ID
	private String userName;//用户名称
	private String userGrade;//用户等级
	private BigDecimal drawMoney;//取现金额
	private BigDecimal drawFee;//取现手续费
	private String bankCode;//银行代码
	private String bankName;//银行名称
	private String drawStatus;//取现状态
	private String drawTime;//取现时间
	private String drawIP;//取现Ip
	
	public Long getDrawLogId() {
		return drawLogId;
	}
	public void setDrawLogId(Long drawLogId) {
		this.drawLogId = drawLogId;
	}
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
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
	public BigDecimal getDrawMoney() {
		return drawMoney;
	}
	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}
	public BigDecimal getDrawFee() {
		return drawFee;
	}
	public void setDrawFee(BigDecimal drawFee) {
		this.drawFee = drawFee;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getDrawStatus() {
		return drawStatus;
	}
	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}
	public String getDrawTime() {
		return drawTime;
	}
	public void setDrawTime(String drawTime) {
		this.drawTime = drawTime;
	}
	public String getDrawIP() {
		return drawIP;
	}
	public void setDrawIP(String drawIP) {
		this.drawIP = drawIP;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
