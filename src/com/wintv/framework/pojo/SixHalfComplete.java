package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 六场半全场
 *
 *
 */
@SuppressWarnings("serial")
public class SixHalfComplete implements Serializable {

	 

	private Long sixHcId;
	private Long three;
	private Long two;
	private Long one;
	private Long betMultiple;
	private Long diviedCopys;
	private String recruitUsers;
	private String privateType;
	private Long autoSubscribe;
	private String isFloor;
	private Long floorCopys;
	private String type;
	private String plan;
	private Long betUserid;
	private Long sponsorUserid;
	private String phase;
	private Date betDate;
	public Long getSixHcId() {
		return sixHcId;
	}
	public void setSixHcId(Long sixHcId) {
		this.sixHcId = sixHcId;
	}
	public Long getThree() {
		return three;
	}
	public void setThree(Long three) {
		this.three = three;
	}
	public Long getTwo() {
		return two;
	}
	public void setTwo(Long two) {
		this.two = two;
	}
	public Long getOne() {
		return one;
	}
	public void setOne(Long one) {
		this.one = one;
	}
	public Long getBetMultiple() {
		return betMultiple;
	}
	public void setBetMultiple(Long betMultiple) {
		this.betMultiple = betMultiple;
	}
	public Long getDiviedCopys() {
		return diviedCopys;
	}
	public void setDiviedCopys(Long diviedCopys) {
		this.diviedCopys = diviedCopys;
	}
	public String getRecruitUsers() {
		return recruitUsers;
	}
	public void setRecruitUsers(String recruitUsers) {
		this.recruitUsers = recruitUsers;
	}
	public String getPrivateType() {
		return privateType;
	}
	public void setPrivateType(String privateType) {
		this.privateType = privateType;
	}
	public Long getAutoSubscribe() {
		return autoSubscribe;
	}
	public void setAutoSubscribe(Long autoSubscribe) {
		this.autoSubscribe = autoSubscribe;
	}
	public String getIsFloor() {
		return isFloor;
	}
	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}
	public Long getFloorCopys() {
		return floorCopys;
	}
	public void setFloorCopys(Long floorCopys) {
		this.floorCopys = floorCopys;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public Long getBetUserid() {
		return betUserid;
	}
	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}
	public Long getSponsorUserid() {
		return sponsorUserid;
	}
	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Date getBetDate() {
		return betDate;
	}
	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

}