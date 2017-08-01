package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
/**
 * 冻结日志
 * 
 *
 */
@SuppressWarnings("serial")
public class VaFrozenLog implements Serializable {
	private Long frozenId;//冻结表主键
	private Long txUserId;//用户ID
	private BigDecimal frozenMoney;//冻结金额
	private Date frozenDate;//
	private String memo;//
	private String txType;//冻结类型
	private BigDecimal allMoney;//
	private BigDecimal mosaicGoldmoney;//
	private BigDecimal frozenMosaicGoldmoney;//冻结彩金
	private Long orderId;//订单号
	/**
	 * 购买彩票(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) 
	 * 购买b2c(10:) |购买c2c(11) 12:充值 13:取款 14缴纳保证金 
	 * orderId与categoryType唯一确定一张表
	 */
	private String categoryType;
	private String orderNo;//订单号 
	private String flg;//'1':冻结   '2':解冻
	public Long getFrozenId() {
		return frozenId;
	}
	public void setFrozenId(Long frozenId) {
		this.frozenId = frozenId;
	}
	public Long getTxUserId() {
		return txUserId;
	}
	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public Date getFrozenDate() {
		return frozenDate;
	}
	public void setFrozenDate(Date frozenDate) {
		this.frozenDate = frozenDate;
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
	public BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public BigDecimal getMosaicGoldmoney() {
		return mosaicGoldmoney;
	}
	public void setMosaicGoldmoney(BigDecimal mosaicGoldmoney) {
		this.mosaicGoldmoney = mosaicGoldmoney;
	}
	public BigDecimal getFrozenMosaicGoldmoney() {
		return frozenMosaicGoldmoney;
	}
	public void setFrozenMosaicGoldmoney(BigDecimal frozenMosaicGoldmoney) {
		this.frozenMosaicGoldmoney = frozenMosaicGoldmoney;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getFlg() {
		return flg;
	}
	public void setFlg(String flg) {
		this.flg = flg;
	}


}