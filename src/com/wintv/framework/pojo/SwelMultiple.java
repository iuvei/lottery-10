package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


@SuppressWarnings("serial")
public class SwelMultiple implements Serializable {

	// Fields

	private Long userid;
	private Long TSwelMultipleId;
	private Long betUserid;
	private String viciType;
	private String viciWay;
	private Integer multiple;
	private Long txMoney;
	private Date betDate;

	// Constructors

	/** default constructor */
	public SwelMultiple() {
	}

	/** full constructor */
	public SwelMultiple(Long TSwelMultipleId,
			Long betUserid, String viciType, String viciWay,
			Integer multiple, Long txMoney, Date betDate) {
		this.TSwelMultipleId = TSwelMultipleId;
		this.betUserid = betUserid;
		this.viciType = viciType;
		this.viciWay = viciWay;
		this.multiple = multiple;
		this.txMoney = txMoney;
		this.betDate = betDate;
	}

	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getTSwelMultipleId() {
		return this.TSwelMultipleId;
	}

	public void setTSwelMultipleId(Long TSwelMultipleId) {
		this.TSwelMultipleId = TSwelMultipleId;
	}

	public Long getBetUserid() {
		return this.betUserid;
	}

	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}

	public String getViciType() {
		return this.viciType;
	}

	public void setViciType(String viciType) {
		this.viciType = viciType;
	}

	public String getViciWay() {
		return this.viciWay;
	}

	public void setViciWay(String viciWay) {
		this.viciWay = viciWay;
	}

	public Integer getMultiple() {
		return this.multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Long getTxMoney() {
		return this.txMoney;
	}

	public void setTxMoney(Long txMoney) {
		this.txMoney = txMoney;
	}

	public Date getBetDate() {
		return this.betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

}