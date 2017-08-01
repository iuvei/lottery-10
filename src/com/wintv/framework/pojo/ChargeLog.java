package com.wintv.framework.pojo;

import java.lang.Long;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class ChargeLog implements java.io.Serializable {
	private Long chargeId;//主键
	private String fromBank;//银行或第三方系统中文名称
	private String fromBankCode;//银行或第三方系统代码
	private BigDecimal money;//金额
	private Long userId;//用户ID
	private Date chargeTime;//日期
	private String memo;//备注
	private String ip;//充值IP
	private String orderNo;//订单号
	private String status;//充值状态
	private String payWay;//充值方式:  '1':支付宝  '2':网银充值 '3':银行转账 '4':手机充值   '5':财付通  '6':快钱  '7':其他
	private BigDecimal allMoney;//总余额
	private String categoryType;//关联哪张表

	public ChargeLog() {
		// TODO Auto-generated constructor stub
	}

	public ChargeLog(Long chargeId, String fromBank, String fromBankCode,
			BigDecimal money, Long userId, Date chargeTime, String memo,
			String ip, String orderNo, String status, String payWay,
			BigDecimal allMoney, String categoryType) {
		this.chargeId = chargeId;
		this.fromBank = fromBank;
		this.fromBankCode = fromBankCode;
		this.money = money;
		this.userId = userId;
		this.chargeTime = chargeTime;
		this.memo = memo;
		this.ip = ip;
		this.orderNo = orderNo;
		this.status = status;
		this.payWay = payWay;
		this.allMoney = allMoney;
		this.categoryType = categoryType;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public String getFromBank() {
		return fromBank;
	}

	public void setFromBank(String fromBank) {
		this.fromBank = fromBank;
	}

	public String getFromBankCode() {
		return fromBankCode;
	}

	public void setFromBankCode(String fromBankCode) {
		this.fromBankCode = fromBankCode;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public BigDecimal getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
}