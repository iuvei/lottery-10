package com.wintv.lottery.pay.vo;

import java.math.BigDecimal;
import java.util.Date;

public class MosaicGoldVo implements java.io.Serializable {
	
	private String txDate;// 交易时间
	private java.math.BigDecimal mosaicGoldAllMoney;// 彩金总余额
	private java.math.BigDecimal txMosaicGold;// 交易彩金
	private String txType;// 交易类型
	private String txName;// 交易类型名称
	private Long orderId;//订单表ID
	private String orderNo;//订单号
	private Long userid;//用户ID
	private String categoryType;//关联哪张订单表
	private Long betId;//方案ID
	private Long xinshuiId;//c2c心水ID
	private Long expertId;//b2c ID

	public MosaicGoldVo() {
		// TODO Auto-generated constructor stub
	}

	public MosaicGoldVo(String txDate, BigDecimal mosaicGoldAllMoney,
			BigDecimal txMosaicGold, String txType, String txName,
			Long orderId, String orderNo, Long userid, String categoryType) {
		super();
		this.txDate = txDate;
		this.mosaicGoldAllMoney = mosaicGoldAllMoney;
		this.txMosaicGold = txMosaicGold;
		this.txType = txType;
		this.txName = txName;
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.userid = userid;
		this.categoryType = categoryType;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public java.math.BigDecimal getMosaicGoldAllMoney() {
		return mosaicGoldAllMoney;
	}

	public void setMosaicGoldAllMoney(java.math.BigDecimal mosaicGoldAllMoney) {
		this.mosaicGoldAllMoney = mosaicGoldAllMoney;
	}

	public java.math.BigDecimal getTxMosaicGold() {
		return txMosaicGold;
	}

	public void setTxMosaicGold(java.math.BigDecimal txMosaicGold) {
		this.txMosaicGold = txMosaicGold;
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
