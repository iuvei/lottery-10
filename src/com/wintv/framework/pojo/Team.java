package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 足球球队的基本信息：国家、城市、成立时间、老板、网址
 * 
 *
 */
@SuppressWarnings("serial")
public class Team implements Serializable {

    private Long id;//主键
	private String name;//球队名称
	private String district;//区域
	private Long districtId;//区域ID 即洲 
	private String country;//国家
	private Long countryId;//国家主键
	private Date foundDate;//成立时间
	private String mainScene;
	private String boss;//老板
	private String city;//所在城市
	private String webSite;//官方网站
	private String contactAddr;//联系地址
	private String stadium;//球场
	private java.math.BigDecimal stadiumVolume;//球场容量
	private String type;//'1':联赛队  '2':国家队  '3':其他队
	private String fullName;//球队全称
	private String email;//球队EMAIL
	
	private String introduction;//球队简介
	private String honor;//球队荣誉
	private String specialty;//球队之最
	
	private Long win;//胜
	private Long ping;//平
	private Long losing;//负
	private Long changci;//场次
	private Long goal;//进球
	private Long point;//积分
	private String img;//队徽存储的地址
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getFoundDate() {
		return foundDate;
	}
	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}
	public String getMainScene() {
		return mainScene;
	}
	public void setMainScene(String mainScene) {
		this.mainScene = mainScene;
	}
	public String getBoss() {
		return boss;
	}
	public void setBoss(String boss) {
		this.boss = boss;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getContactAddr() {
		return contactAddr;
	}
	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}
	public String getStadium() {
		return stadium;
	}
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}
	public java.math.BigDecimal getStadiumVolume() {
		return stadiumVolume;
	}
	public void setStadiumVolume(java.math.BigDecimal stadiumVolume) {
		this.stadiumVolume = stadiumVolume;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getHonor() {
		return honor;
	}
	public void setHonor(String honor) {
		this.honor = honor;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public Long getWin() {
		return win;
	}
	public void setWin(Long win) {
		this.win = win;
	}
	public Long getPing() {
		return ping;
	}
	public void setPing(Long ping) {
		this.ping = ping;
	}
	public Long getLosing() {
		return losing;
	}
	public void setLosing(Long losing) {
		this.losing = losing;
	}
	public Long getChangci() {
		return changci;
	}
	public void setChangci(Long changci) {
		this.changci = changci;
	}
	public Long getGoal() {
		return goal;
	}
	public void setGoal(Long goal) {
		this.goal = goal;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(Long point) {
		this.point = point;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}