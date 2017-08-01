package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class ConcedeWinningLosing implements Serializable {


	private Long tgId;
	private String wiciType;
	private String isDingdan;
	private String viciWay;
	private Long betMultiple;
	private String phase;
	private Date betTime;
	private Long betUserid;
	private String plan;
	private String type;
	private Long sponsorUserid;
	private String isFloor;
	private String formatStr;
	private java.math.BigDecimal singleMoney;
	private java.math.BigDecimal allMoney;
	private Long divideCopys;
	private java.math.BigDecimal floorCopys;
	private String privateType;
	private String recruitUsers;

	
	public ConcedeWinningLosing() {
	}

	 
	public ConcedeWinningLosing(String wiciType, String isDingdan,
			String viciWay, Long betMultiple, String phase, Date betTime,
			Long betUserid, String plan, String type, Long sponsorUserid,
			String isFloor, String formatStr, java.math.BigDecimal singleMoney,
			java.math.BigDecimal allMoney, Long divideCopys, java.math.BigDecimal floorCopys,
			String privateType, String recruitUsers) {
		this.wiciType = wiciType;
		this.isDingdan = isDingdan;
		this.viciWay = viciWay;
		this.betMultiple = betMultiple;
		this.phase = phase;
		this.betTime = betTime;
		this.betUserid = betUserid;
		this.plan = plan;
		this.type = type;
		this.sponsorUserid = sponsorUserid;
		this.isFloor = isFloor;
		this.formatStr = formatStr;
		this.singleMoney = singleMoney;
		this.allMoney = allMoney;
		this.divideCopys = divideCopys;
		this.floorCopys = floorCopys;
		this.privateType = privateType;
		this.recruitUsers = recruitUsers;
	}

	 

	public Long getTgId() {
		return this.tgId;
	}

	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	public String getWiciType() {
		return this.wiciType;
	}

	public void setWiciType(String wiciType) {
		this.wiciType = wiciType;
	}

	public String getIsDingdan() {
		return this.isDingdan;
	}

	public void setIsDingdan(String isDingdan) {
		this.isDingdan = isDingdan;
	}

	public String getViciWay() {
		return this.viciWay;
	}

	public void setViciWay(String viciWay) {
		this.viciWay = viciWay;
	}

	public Long getBetMultiple() {
		return this.betMultiple;
	}

	public void setBetMultiple(Long betMultiple) {
		this.betMultiple = betMultiple;
	}

	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Date getBetTime() {
		return this.betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public Long getBetUserid() {
		return this.betUserid;
	}

	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSponsorUserid() {
		return this.sponsorUserid;
	}

	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
	}

	public String getIsFloor() {
		return this.isFloor;
	}

	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}

	public String getFormatStr() {
		return this.formatStr;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}

	public java.math.BigDecimal getSingleMoney() {
		return this.singleMoney;
	}

	public void setSingleMoney(java.math.BigDecimal singleMoney) {
		this.singleMoney = singleMoney;
	}

	public java.math.BigDecimal getAllMoney() {
		return this.allMoney;
	}

	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	public Long getDivideCopys() {
		return this.divideCopys;
	}

	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}

	public java.math.BigDecimal getFloorCopys() {
		return this.floorCopys;
	}

	public void setFloorCopys(java.math.BigDecimal floorCopys) {
		this.floorCopys = floorCopys;
	}

	public String getPrivateType() {
		return this.privateType;
	}

	public void setPrivateType(String privateType) {
		this.privateType = privateType;
	}

	public String getRecruitUsers() {
		return this.recruitUsers;
	}

	public void setRecruitUsers(String recruitUsers) {
		this.recruitUsers = recruitUsers;
	}

}