package com.wintv.lottery.pay.vo;

import java.math.BigDecimal;
import java.util.Date;

public class MoneyDetailVo implements java.io.Serializable {

	private java.math.BigDecimal txMoney;// 金额
	private String txDate;// 交易时间
	private java.math.BigDecimal allMoney;// 总余额
	private String txType;// 交易类型
	private String txName;// 交易类型名称
	private Long orderId;// 订单表ID
	private String orderNo;// 订单号
	private Long userid;//用户ID
	private String categoryType;//关联哪张订单表
	private Long betId;//方案ID
	private Long xinshuiId;//c2c心水ID
	private Long expertId;//b2c ID
	
	public MoneyDetailVo() {
		// TODO Auto-generated constructor stub
	}

	public MoneyDetailVo(BigDecimal txMoney, String txDate,
			BigDecimal allMoney, String txType, String txName, Long orderId,
			String orderNo, Long userid, String categoryType) {
		super();
		this.txMoney = txMoney;
		this.txDate = txDate;
		this.allMoney = allMoney;
		this.txType = txType;
		this.txName = txName;
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.userid = userid;
		this.categoryType = categoryType;
	}

	public java.math.BigDecimal getTxMoney() {
		return txMoney;
	}

	public void setTxMoney(java.math.BigDecimal txMoney) {
		this.txMoney = txMoney;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public java.math.BigDecimal getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTxName() {
		return txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public Long getXinshuiId() {
		return xinshuiId;
	}

	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}
}
