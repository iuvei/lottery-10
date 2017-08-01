package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class WinningLosing implements Serializable {

	

	private Long wlId;
	private Long userid;
	private Long sponsorUserid;
	private Long betMultiple;
	private java.math.BigDecimal divideCopys;
	private String recruitUsers;
	private String isPrivate;
	private String isFloor;
	private Long subscribeCopys;
	private Long betNum;
	private String plan;
	private Date betDate;
	private Long parentId;
	private java.math.BigDecimal floorCopys;
	private java.math.BigDecimal autoMoney;
	private java.math.BigDecimal allMoney;
	private String type;
	private String planCode;
	private String phase;

	 

	 
	public WinningLosing() {
	}

	 
	public WinningLosing(Long userid, Long sponsorUserid, Long betMultiple,
			java.math.BigDecimal divideCopys, String recruitUsers, String isPrivate,
			String isFloor, Long subscribeCopys, Long betNum, String plan,
			Date betDate, Long parentId, java.math.BigDecimal floorCopys, java.math.BigDecimal autoMoney,
			java.math.BigDecimal allMoney, String type, String planCode) {
		this.userid = userid;
		this.sponsorUserid = sponsorUserid;
		this.betMultiple = betMultiple;
		this.divideCopys = divideCopys;
		this.recruitUsers = recruitUsers;
		this.isPrivate = isPrivate;
		this.isFloor = isFloor;
		this.subscribeCopys = subscribeCopys;
		this.betNum = betNum;
		this.plan = plan;
		this.betDate = betDate;
		this.parentId = parentId;
		this.floorCopys = floorCopys;
		this.autoMoney = autoMoney;
		this.allMoney = allMoney;
		this.type = type;
		this.planCode = planCode;
	}

	 

	public Long getWlId() {
		return this.wlId;
	}

	public void setWlId(Long wlId) {
		this.wlId = wlId;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getSponsorUserid() {
		return this.sponsorUserid;
	}

	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
	}

	public Long getBetMultiple() {
		return this.betMultiple;
	}

	public void setBetMultiple(Long betMultiple) {
		this.betMultiple = betMultiple;
	}

	public java.math.BigDecimal getDivideCopys() {
		return this.divideCopys;
	}

	public void setDivideCopys(java.math.BigDecimal divideCopys) {
		this.divideCopys = divideCopys;
	}

	public String getRecruitUsers() {
		return this.recruitUsers;
	}

	public void setRecruitUsers(String recruitUsers) {
		this.recruitUsers = recruitUsers;
	}

	public String getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getIsFloor() {
		return this.isFloor;
	}

	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}

	public Long getSubscribeCopys() {
		return this.subscribeCopys;
	}

	public void setSubscribeCopys(Long subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}

	public Long getBetNum() {
		return this.betNum;
	}

	public void setBetNum(Long betNum) {
		this.betNum = betNum;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Date getBetDate() {
		return this.betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public java.math.BigDecimal getFloorCopys() {
		return this.floorCopys;
	}

	public void setFloorCopys(java.math.BigDecimal floorCopys) {
		this.floorCopys = floorCopys;
	}

	public java.math.BigDecimal getAutoMoney() {
		return this.autoMoney;
	}

	public void setAutoMoney(java.math.BigDecimal autoMoney) {
		this.autoMoney = autoMoney;
	}

	public java.math.BigDecimal getAllMoney() {
		return this.allMoney;
	}

	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlanCode() {
		return this.planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}


	public String getPhase() {
		return phase;
	}


	public void setPhase(String phase) {
		this.phase = phase;
	}

}