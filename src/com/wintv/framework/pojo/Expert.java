package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
/**
 * B2C专家表
 */
@SuppressWarnings("serial")
public class Expert implements Serializable {
	private String expertClass;//专家分类
    private Long expertId;//主键
	private String introduction;//专家介绍 
	private String job;//职务
    private Double winRatio;//胜率
	private Date contractTime;//签约时间
	private String type;//"1": 推荐   "0": 普通专家
	private String expertName;
	private BigDecimal monthPack;//包周
	private BigDecimal weekPack;
	private BigDecimal seasonPack;
	private BigDecimal yearPack;
	private String status;
	private String photo;//照片路径,例如/upload/b2c/photo/userId/**.gif,jpg
	private String expertCode;//内参编号
	private String isValuePack;//是否为超值包   '1':投资超值包   '0':不是 
	private String skilledRace;//擅长联赛
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
	
	public Double getWinRatio() {
		return winRatio;
	}
	public void setWinRatio(Double winRatio) {
		this.winRatio = winRatio;
	}
	public Date getContractTime() {
		return contractTime;
	}
	public void setContractTime(Date contractTime) {
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
	public BigDecimal getMonthPack() {
		return monthPack;
	}
	public void setMonthPack(BigDecimal monthPack) {
		this.monthPack = monthPack;
	}
	public BigDecimal getWeekPack() {
		return weekPack;
	}
	public void setWeekPack(BigDecimal weekPack) {
		this.weekPack = weekPack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getExpertCode() {
		return expertCode;
	}
	public void setExpertCode(String expertCode) {
		this.expertCode = expertCode;
	}
	public String getIsValuePack() {
		return isValuePack;
	}
	public void setIsValuePack(String isValuePack) {
		this.isValuePack = isValuePack;
	}
	public BigDecimal getSeasonPack() {
		return seasonPack;
	}
	public void setSeasonPack(BigDecimal seasonPack) {
		this.seasonPack = seasonPack;
	}
	public BigDecimal getYearPack() {
		return yearPack;
	}
	public void setYearPack(BigDecimal yearPack) {
		this.yearPack = yearPack;
	}
	
	public String getExpertClass() {
		return expertClass;
	}
	public void setExpertClass(String expertClass) {
		this.expertClass = expertClass;
	}
	public String getSkilledRace() {
		return skilledRace;
	}
	public void setSkilledRace(String skilledRace) {
		this.skilledRace = skilledRace;
	}
	
}