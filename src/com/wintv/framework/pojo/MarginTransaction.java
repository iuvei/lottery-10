package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


@SuppressWarnings("serial")
public class MarginTransaction implements Serializable {

	// Fields

	private Long mtId;
	private Long txUserId;
	private Long txMoney;
	private String isThaw;
	private Date frozenTime;
	private String purpose;

	// Constructors

	/** default constructor */
	public MarginTransaction() {
	}

	/** minimal constructor */
	public MarginTransaction(Long txUserId, Long txMoney,
			String isThaw, Date frozenTime) {
		this.txUserId = txUserId;
		this.txMoney = txMoney;
		this.isThaw = isThaw;
		this.frozenTime = frozenTime;
	}

	/** full constructor */
	public MarginTransaction(Long txUserId, Long txMoney,
			String isThaw, Date frozenTime, String purpose) {
		this.txUserId = txUserId;
		this.txMoney = txMoney;
		this.isThaw = isThaw;
		this.frozenTime = frozenTime;
		this.purpose = purpose;
	}

	// Property accessors

	public Long getMtId() {
		return this.mtId;
	}

	public void setMtId(Long mtId) {
		this.mtId = mtId;
	}

	public Long getTxUserId() {
		return this.txUserId;
	}

	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}

	public Long getTxMoney() {
		return this.txMoney;
	}

	public void setTxMoney(Long txMoney) {
		this.txMoney = txMoney;
	}

	public String getIsThaw() {
		return this.isThaw;
	}

	public void setIsThaw(String isThaw) {
		this.isThaw = isThaw;
	}

	public Date getFrozenTime() {
		return this.frozenTime;
	}

	public void setFrozenTime(Date frozenTime) {
		this.frozenTime = frozenTime;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}