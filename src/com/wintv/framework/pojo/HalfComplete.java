package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
/**
 * ��ȫ��
 * @author Administrator
 *
 */
@SuppressWarnings("serial")

public class HalfComplete implements Serializable {

	

	private Long hcId;//主键
	private String viciWay;//过关方式
	private String formatStr;//格式转换字符
	private java.math.BigDecimal singleMoney;//总金额
	private Long multiple;//
	private String plan;//
	private Long dividedCopys;//
	private java.math.BigDecimal deduct;//
	private Long subscribeCopys;//
	private String isFloor;//
	private String isOpen;//
	private String recruitUsers;//
	private String type;//51:单代  52 单合  53  复代   54 复合
	private Long betUserId;//
	private Date betDate;//
	private Long parentId;//
	private java.math.BigDecimal allMoney;
	
	private Set<HalfCompleteChoice> halfCompleteChoices;//半全场场次选择

	public Long getHcId() {
		return hcId;
	}

	public void setHcId(Long hcId) {
		this.hcId = hcId;
	}

	public String getViciWay() {
		return viciWay;
	}

	public void setViciWay(String viciWay) {
		this.viciWay = viciWay;
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

	public Long getMultiple() {
		return multiple;
	}

	public void setMultiple(Long multiple) {
		this.multiple = multiple;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Long getDividedCopys() {
		return dividedCopys;
	}

	public void setDividedCopys(Long dividedCopys) {
		this.dividedCopys = dividedCopys;
	}

	public java.math.BigDecimal getDeduct() {
		return deduct;
	}

	public void setDeduct(java.math.BigDecimal deduct) {
		this.deduct = deduct;
	}

	public Long getSubscribeCopys() {
		return subscribeCopys;
	}

	public void setSubscribeCopys(Long subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}

	public String getIsFloor() {
		return isFloor;
	}

	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getBetUserId() {
		return betUserId;
	}

	public void setBetUserId(Long betUserId) {
		this.betUserId = betUserId;
	}

	public Date getBetDate() {
		return betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Set<HalfCompleteChoice> getHalfCompleteChoices() {
		return halfCompleteChoices;
	}

	public void setHalfCompleteChoices(Set<HalfCompleteChoice> halfCompleteChoices) {
		this.halfCompleteChoices = halfCompleteChoices;
	}

	public java.math.BigDecimal getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	
}