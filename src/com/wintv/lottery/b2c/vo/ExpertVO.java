package com.wintv.lottery.b2c.vo;


import java.io.Serializable;
@SuppressWarnings("serial")
public class ExpertVO implements Serializable{
	
	private Long expertId;//主键
	private String introduction;//专家介绍 
	private String job;//职务
	private Long userId;//用户ID
	private Double winRatio;//胜率
	private String contractTime;//签约时间
	private String type;//'1':特约专家
	private String expertName;
	private String monthPack;//包月价格
	private String weekPack;//包周价格
	private String seasonPack;//包季价格
	private String yearPack;//包年价格
	private String status;//状态
	private long ncCnt;//内参总数
	private String euAll;
	private String euMonth;
	private String asiaAll;
	private String asiaMonth;
	private String bigsamllAll;
	private String bigsamllMonth;
	private String packMonthOrderCntAll;
	private String packMonthOrderCntMonth;
	private String packWeekOrderCntAll;
	private String packWeekOrderCntMonth;
	private String soldMoney;
	private String lastPub;
	private String expertCode;//内参编号
	private String photo;//照片路径,例如/upload/b2c/photo/userId/**.gif,jpg
	private String expertClass;//专家分类
	private String isValuePack;
	public Long getExpertId() {
		return expertId;
	}
	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getWinRatio() {
		return winRatio;
	}
	public void setWinRatio(Double winRatio) {
		this.winRatio = winRatio;
	}
	public String getContractTime() {
		return contractTime;
	}
	public void setContractTime(String contractTime) {
		this.contractTime = contractTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getMonthPack() {
		return monthPack;
	}
	public void setMonthPack(String monthPack) {
		this.monthPack = monthPack;
	}
	public String getWeekPack() {
		return weekPack;
	}
	public void setWeekPack(String weekPack) {
		this.weekPack = weekPack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getNcCnt() {
		return ncCnt;
	}
	public void setNcCnt(long ncCnt) {
		this.ncCnt = ncCnt;
	}
	public String getEuAll() {
		return euAll;
	}
	public void setEuAll(String euAll) {
		this.euAll = euAll;
	}
	public String getEuMonth() {
		return euMonth;
	}
	public void setEuMonth(String euMonth) {
		this.euMonth = euMonth;
	}
	public String getAsiaAll() {
		return asiaAll;
	}
	public void setAsiaAll(String asiaAll) {
		this.asiaAll = asiaAll;
	}
	public String getAsiaMonth() {
		return asiaMonth;
	}
	public void setAsiaMonth(String asiaMonth) {
		this.asiaMonth = asiaMonth;
	}
	public String getBigsamllAll() {
		return bigsamllAll;
	}
	public void setBigsamllAll(String bigsamllAll) {
		this.bigsamllAll = bigsamllAll;
	}
	public String getBigsamllMonth() {
		return bigsamllMonth;
	}
	public void setBigsamllMonth(String bigsamllMonth) {
		this.bigsamllMonth = bigsamllMonth;
	}
	public String getPackMonthOrderCntAll() {
		return packMonthOrderCntAll;
	}
	public void setPackMonthOrderCntAll(String packMonthOrderCntAll) {
		this.packMonthOrderCntAll = packMonthOrderCntAll;
	}
	public String getPackMonthOrderCntMonth() {
		return packMonthOrderCntMonth;
	}
	public void setPackMonthOrderCntMonth(String packMonthOrderCntMonth) {
		this.packMonthOrderCntMonth = packMonthOrderCntMonth;
	}
	public String getPackWeekOrderCntAll() {
		return packWeekOrderCntAll;
	}
	public void setPackWeekOrderCntAll(String packWeekOrderCntAll) {
		this.packWeekOrderCntAll = packWeekOrderCntAll;
	}
	public String getPackWeekOrderCntMonth() {
		return packWeekOrderCntMonth;
	}
	public void setPackWeekOrderCntMonth(String packWeekOrderCntMonth) {
		this.packWeekOrderCntMonth = packWeekOrderCntMonth;
	}
	public String getSoldMoney() {
		return soldMoney;
	}
	public void setSoldMoney(String soldMoney) {
		this.soldMoney = soldMoney;
	}
	public String getLastPub() {
		return lastPub;
	}
	public void setLastPub(String lastPub) {
		this.lastPub = lastPub;
	}
	public String getExpertCode() {
		return expertCode;
	}
	public void setExpertCode(String expertCode) {
		this.expertCode = expertCode;
	}
	
	public String getSeasonPack() {
		return seasonPack;
	}
	public void setSeasonPack(String seasonPack) {
		this.seasonPack = seasonPack;
	}
	public String getYearPack() {
		return yearPack;
	}
	public void setYearPack(String yearPack) {
		this.yearPack = yearPack;
	}

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getExpertClass() {
		return expertClass;
	}
	public void setExpertClass(String expertClass) {
		this.expertClass = expertClass;
	}
	public String getIsValuePack() {
		return isValuePack;
	}
	public void setIsValuePack(String isValuePack) {
		this.isValuePack = isValuePack;
	}
	
}
