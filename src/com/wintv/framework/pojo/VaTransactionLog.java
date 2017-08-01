package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.math.BigDecimal;
import java.util.Date;


@SuppressWarnings("serial")
public class VaTransactionLog implements Serializable {
    private Long vatId;//主键
	private Long txUserId;//用户ID
	private java.math.BigDecimal txMoney;//金额
	private Date txDate;//交易时间
	private String memo;//备注
	private String txType;//详细请见  T_DICTIONARY表TX_TYPE字段所对应的几条记录
	private java.math.BigDecimal allMoney;//总余额
	private java.math.BigDecimal mosaicGoldAllMoney;//彩金总余额
	private java.math.BigDecimal txMosaicGold;//交易彩金
	private Long orderId;//关联的表对应的ID(投注表   心水购买表)
	private String categoryType;//日志类型  购买彩票(1:让球胜平负,2：总进球数,3:上下单双,4:比分,5：半全场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11)
	private String flg;//'1':进账  '2':出账
	private String orderNo;//真正订单号
	private String txTypeName;//交易类型中文名称
	private Long targetUserId;//当进行c2c交易扣款时，民间高手的用户ID
	public VaTransactionLog() {
		
	}

	public VaTransactionLog(Long vatId, Long txUserId, BigDecimal txMoney,
			Date txDate, String memo, String txType, BigDecimal allMoney,
			BigDecimal mosaicGoldAllMoney, BigDecimal txMosaicGold,
			Long orderId, String categoryType, String flg, String orderNo,
			String txTypeName) {
		this.vatId = vatId;
		this.txUserId = txUserId;
		this.txMoney = txMoney;
		this.txDate = txDate;
		this.memo = memo;
		this.txType = txType;
		this.allMoney = allMoney;
		this.mosaicGoldAllMoney = mosaicGoldAllMoney;
		this.txMosaicGold = txMosaicGold;
		this.orderId = orderId;
		this.categoryType = categoryType;
		this.flg = flg;
		this.orderNo = orderNo;
		this.txTypeName = txTypeName;
	}

	public Long getVatId() {
		return vatId;
	}

	public void setVatId(Long vatId) {
		this.vatId = vatId;
	}

	public Long getTxUserId() {
		return txUserId;
	}

	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}

	public java.math.BigDecimal getTxMoney() {
		return txMoney;
	}

	public void setTxMoney(java.math.BigDecimal txMoney) {
		this.txMoney = txMoney;
	}

	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public java.math.BigDecimal getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTxTypeName() {
		return txTypeName;
	}

	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
}