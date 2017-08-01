package com.wintv.framework.pojo;

import java.io.Serializable;

/**
 * ��ȫ��������
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class HalfCompleteChoice implements Serializable {

	private Long hccId;
	private HalfComplete halfComplete;//半全场
	private Long hccScheduleId;
	private String plan;
	private String isDingdan;
	private String viciType;
	private String viciWay;
	private Long betMultiple;
	private java.math.BigDecimal betMoney;
	private Long betNum;
	public Long getHccId() {
		return hccId;
	}
	public void setHccId(Long hccId) {
		this.hccId = hccId;
	}
	public HalfComplete getHalfComplete() {
		return halfComplete;
	}
	public void setHalfComplete(HalfComplete halfComplete) {
		this.halfComplete = halfComplete;
	}
	public Long getHccScheduleId() {
		return hccScheduleId;
	}
	public void setHccScheduleId(Long hccScheduleId) {
		this.hccScheduleId = hccScheduleId;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getIsDingdan() {
		return isDingdan;
	}
	public void setIsDingdan(String isDingdan) {
		this.isDingdan = isDingdan;
	}
	public String getViciType() {
		return viciType;
	}
	public void setViciType(String viciType) {
		this.viciType = viciType;
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
	public java.math.BigDecimal getBetMoney() {
		return betMoney;
	}
	public void setBetMoney(java.math.BigDecimal betMoney) {
		this.betMoney = betMoney;
	}
	public Long getBetNum() {
		return betNum;
	}
	public void setBetNum(Long betNum) {
		this.betNum = betNum;
	}


}