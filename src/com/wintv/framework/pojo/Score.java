package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 总进球投注结果
 * 
 *
 */
@SuppressWarnings("serial")
public class Score implements Serializable {
    
	private Long scoreId;
	private String wiciType;
	private String isDingdan;
	private String viciWay;
	private Long betMultiple;
	private String phase;
	private Date betTime;
	private Long betUserid;
	private Long betMulti;
	private String plan;
	private String type;
	private Long sponsorUserid;
	private String isFloor;
	private String dingdan;
	private String formatStr;
	private java.math.BigDecimal singleMoney;
	private java.math.BigDecimal allMoney;
	private Long divideCopys;
	private java.math.BigDecimal floorCopys;
	private String isOpen;
	private String recruitUsers;
	private Set  scoreChoices = new HashSet(0);
	public Long getScoreId() {
		return scoreId;
	}
	public void setScoreId(Long scoreId) {
		this.scoreId = scoreId;
	}
	public String getWiciType() {
		return wiciType;
	}
	public void setWiciType(String wiciType) {
		this.wiciType = wiciType;
	}
	public String getIsDingdan() {
		return isDingdan;
	}
	public void setIsDingdan(String isDingdan) {
		this.isDingdan = isDingdan;
	}
	public String getViciWay() {
		return viciWay;
	}
	public void setViciWay(String viciWay) {
		this.viciWay = viciWay;
	}
	public Long getBetMultiple() {
		return betMultiple;
	}
	public void setBetMultiple(Long betMultiple) {
		this.betMultiple = betMultiple;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Date getBetTime() {
		return betTime;
	}
	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}
	public Long getBetUserid() {
		return betUserid;
	}
	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}
	public Long getBetMulti() {
		return betMulti;
	}
	public void setBetMulti(Long betMulti) {
		this.betMulti = betMulti;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getSponsorUserid() {
		return sponsorUserid;
	}
	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
	}
	public String getIsFloor() {
		return isFloor;
	}
	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}
	public String getDingdan() {
		return dingdan;
	}
	public void setDingdan(String dingdan) {
		this.dingdan = dingdan;
	}
	public String getFormatStr() {
		return formatStr;
	}
	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}
	public java.math.BigDecimal getSingleMoney() {
		return singleMoney;
	}
	public void setSingleMoney(java.math.BigDecimal singleMoney) {
		this.singleMoney = singleMoney;
	}
	public java.math.BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public Long getDivideCopys() {
		return divideCopys;
	}
	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}
	public java.math.BigDecimal getFloorCopys() {
		return floorCopys;
	}
	public void setFloorCopys(java.math.BigDecimal floorCopys) {
		this.floorCopys = floorCopys;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getRecruitUsers() {
		return recruitUsers;
	}
	public void setRecruitUsers(String recruitUsers) {
		this.recruitUsers = recruitUsers;
	}
	public Set getScoreChoices() {
		return scoreChoices;
	}
	public void setScoreChoices(Set scoreChoices) {
		this.scoreChoices = scoreChoices;
	}

}