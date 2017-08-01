package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 
 * 用户取款日志
 *
 */
@SuppressWarnings("serial")
public class WithdrawLog implements Serializable {
	private Long drawId;//主键
	private String bank;//银行名称
	private BigDecimal txMoney;//取款金额
	private Long txUserId;//取款人用户ID
	private Date drawTime;//取款日期
	private String bankProvince;//银行所在省份
	private String bankCity;//银行所在城市
	private String status;//状态  1:未受理、2:已受理、3:已转账
	private String memo;//备注
	private BigDecimal fee;//手续费
	private String bankCode;//银行代码
    private String orderNo;//取款订单号
	private String withdrawIp;  //取款IP
	private BigDecimal allMoney;//总余额
	private String categoryType;//关联哪张表
	
	
	private String branch;// 银行支行
	private String cardNum;// 银行账户
	
	public WithdrawLog() {
	
	}

	public WithdrawLog(Long drawId, String bank, BigDecimal txMoney,
			Long txUserId, Date drawTime, String bankProvince, String bankCity,
			String status, String memo, BigDecimal fee, String bankCode,
			String orderNo, String withdrawIp, BigDecimal allMoney,
			String categoryType) {
		this.drawId = drawId;
		this.bank = bank;
		this.txMoney = txMoney;
		this.txUserId = txUserId;
		this.drawTime = drawTime;
		this.bankProvince = bankProvince;
		this.bankCity = bankCity;
		this.status = status;
		this.memo = memo;
		this.fee = fee;
		this.bankCode = bankCode;
		this.orderNo = orderNo;
		this.withdrawIp = withdrawIp;
		this.allMoney = allMoney;
		this.categoryType = categoryType;
	}

	public Long getDrawId() {
		return drawId;
	}

	public void setDrawId(Long drawId) {
		this.drawId = drawId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public BigDecimal getTxMoney() {
		return txMoney;
	}

	public void setTxMoney(BigDecimal txMoney) {
		this.txMoney = txMoney;
	}

	public Long getTxUserId() {
		return txUserId;
	}

	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWithdrawIp() {
		return withdrawIp;
	}

	public void setWithdrawIp(String withdrawIp) {
		this.withdrawIp = withdrawIp;
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
}