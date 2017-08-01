package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 四场进球
 * 
 *
 */
@SuppressWarnings("serial")
public class FourSceneGoal implements Serializable {

	private Long sgId;//主键
	private String no;//号码(使用逗号隔开每一场结果) 与方案同一意思
	private Long multiple;//倍数
	private Long copys;//份数
	private String recruitUsers;//招股对象
	private String privateType;//保密类型
	private Long autoSubscribe;//认购份数
	private String isFloor;//是否保底
	private Long sponsorUserid;//发起人用户ID
	private Long userId;//投注人ID
	private Date betTime;//投注时间
	private String phase;//第几期
	private Long parentId;//发起方案ID(当参与合买时关联本表主键否则为空)
	private String type;//类型 91单代 92 单合  93 复代 94复合
	private java.math.BigDecimal allMoney;//总金额
	private java.math.BigDecimal autoSubscribeMoney;//自认金额
	public Long getSgId() {
		return sgId;
	}
	public void setSgId(Long sgId) {
		this.sgId = sgId;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Long getMultiple() {
		return multiple;
	}
	public void setMultiple(Long multiple) {
		this.multiple = multiple;
	}
	public Long getCopys() {
		return copys;
	}
	public void setCopys(Long copys) {
		this.copys = copys;
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
	public Long getSponsorUserid() {
		return sponsorUserid;
	}
	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getBetTime() {
		return betTime;
	}
	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public java.math.BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public java.math.BigDecimal getAutoSubscribeMoney() {
		return autoSubscribeMoney;
	}
	public void setAutoSubscribeMoney(java.math.BigDecimal autoSubscribeMoney) {
		this.autoSubscribeMoney = autoSubscribeMoney;
	}

	
}