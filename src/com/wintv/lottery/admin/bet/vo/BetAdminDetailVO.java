package com.wintv.lottery.admin.bet.vo;

import java.util.Date;

public class BetAdminDetailVO {
	private Long betId;//主键
	private String wiciType;//过关类型
	private String isDingdan;//是否定胆
	private String viciWay;//过关方式
	private Long betNumber;//注数
	
	private String phaseNo;//期次
	private String betTime;//投注时间
	private Long betUserid;//投注人用户ID
	private Long betMulti;//倍数
	private String plan;//方案(胜胜 33 ..其他类似)
	private String type;//类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	private String sponsorType;//发起类型   '1':发起人   '2'  参与合买
    private String isFloor;//是否保底
	private String planCode;//方案编号
	private String formatStr;//格式转换字符
	private String singleMoney;//单倍金额
	private String allMoney;//总金额
	private Long divideCopys;//分成份数
	private String floorCopys;//保底份数
	private String isOpen;//是否公开
	private String recruitUsers;//招股对象

	private String orderNo;//订单号
	private String betCategory;//彩种
	
	private String orderStatus;//订单状态

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
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

	public Long getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(Long betNumber) {
		this.betNumber = betNumber;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public String getBetTime() {
		return betTime;
	}

	public void setBetTime(String betTime) {
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

	public String getSponsorType() {
		return sponsorType;
	}

	public void setSponsorType(String sponsorType) {
		this.sponsorType = sponsorType;
	}

	public String getIsFloor() {
		return isFloor;
	}

	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getFormatStr() {
		return formatStr;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}

	public String getSingleMoney() {
		return singleMoney;
	}

	public void setSingleMoney(String singleMoney) {
		this.singleMoney = singleMoney;
	}

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public Long getDivideCopys() {
		return divideCopys;
	}

	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}

	public String getFloorCopys() {
		return floorCopys;
	}

	public void setFloorCopys(String floorCopys) {
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
