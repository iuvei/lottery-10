package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


public class SwelSingleCoop implements Serializable {

	// Fields

	private Long swelSingleCoopId;
	private Long sponsorUserid;
	private Long betUserid;
	private String planCode;
	private Long subscribeCopys;
	private Date betDate;
	private Byte isTop;

	// Constructors

	/** default constructor */
	public SwelSingleCoop() {
	}

	/** full constructor */
	public SwelSingleCoop(Long sponsorUserid,
			Long betUserid, String planCode, Long subscribeCopys,
			Date betDate, Byte isTop) {
		this.sponsorUserid = sponsorUserid;
		this.betUserid = betUserid;
		this.planCode = planCode;
		this.subscribeCopys = subscribeCopys;
		this.betDate = betDate;
		this.isTop = isTop;
	}

	// Property accessors

	public Long getSwelSingleCoopId() {
		return this.swelSingleCoopId;
	}

	public void setSwelSingleCoopId(Long swelSingleCoopId) {
		this.swelSingleCoopId = swelSingleCoopId;
	}

	public Long getSponsorUserid() {
		return this.sponsorUserid;
	}

	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
	}

	public Long getBetUserid() {
		return this.betUserid;
	}

	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}

	public String getPlanCode() {
		return this.planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Long getSubscribeCopys() {
		return this.subscribeCopys;
	}

	public void setSubscribeCopys(Long subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}

	public Date getBetDate() {
		return this.betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	public Byte getIsTop() {
		return this.isTop;
	}

	public void setIsTop(Byte isTop) {
		this.isTop = isTop;
	}

}