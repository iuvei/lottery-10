package com.wintv.lottery.admin.phase.vo;

import java.util.Date;


public class LotteryPhasePO {
	private Long id;//主键
	private String soldTime;//开售时间
	private String phaseNo;//期次
	private String mulDeadline;//复式截止时间
	private String singleDeadline;//单式截止时间
	private String tkpDeadline;//打票截至时间
	private String isCurrent;//是否为当前期次    同一彩种  同一时刻  不可能存在两种 "当期期次"
	private String category;//期次分类  '1':胜负彩   '2':进球彩期次 '3':半全场期次 '4':北京单场期次  '5':竞彩单场
	private String status;//期次状态 默认为'1'  期次未审核
	private String username;//期次添加人
	private String addTime;//添加时间
    private String viciDeadline;//普通过关截止时间(只针对北京单场)
	private Long comboVici;//组合与自由过关(只针对北京单场)
	private String agianstIds;
	private String phaseType;//需要判断
	private String kjNo;//开奖号码
	private Date  kjTime;//开奖日期
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSoldTime() {
		return soldTime;
	}
	public void setSoldTime(String soldTime) {
		this.soldTime = soldTime;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getMulDeadline() {
		return mulDeadline;
	}
	public void setMulDeadline(String mulDeadline) {
		this.mulDeadline = mulDeadline;
	}
	
	public String getTkpDeadline() {
		return tkpDeadline;
	}
	public void setTkpDeadline(String tkpDeadline) {
		this.tkpDeadline = tkpDeadline;
	}
	public String getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getViciDeadline() {
		return viciDeadline;
	}
	public void setViciDeadline(String viciDeadline) {
		this.viciDeadline = viciDeadline;
	}
	public Long getComboVici() {
		return comboVici;
	}
	public void setComboVici(Long comboVici) {
		this.comboVici = comboVici;
	}
	public String getAgianstIds() {
		return agianstIds;
	}
	public void setAgianstIds(String agianstIds) {
		this.agianstIds = agianstIds;
	}
	public String getSingleDeadline() {
		return singleDeadline;
	}
	public void setSingleDeadline(String singleDeadline) {
		this.singleDeadline = singleDeadline;
	}
	public String getPhaseType() {
		return phaseType;
	}
	public void setPhaseType(String phaseType) {
		this.phaseType = phaseType;
	}
	public String getKjNo() {
		return kjNo;
	}
	public void setKjNo(String kjNo) {
		this.kjNo = kjNo;
	}
	public Date getKjTime() {
		return kjTime;
	}
	public void setKjTime(Date kjTime) {
		this.kjTime = kjTime;
	}
}
