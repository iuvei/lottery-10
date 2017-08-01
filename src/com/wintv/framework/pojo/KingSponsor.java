package com.wintv.framework.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 金牌发起人申请记录
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class KingSponsor implements Serializable {
    
	private Long id;//主键
	private String username;//金牌发起人用户名
	private String betCategory;//彩种
	private String betCategoryValue;//彩种数值   1,2,3,5,61,62,63,64,65  2010-04-29 13:50新增
	private Long betMilitary;//投注战绩
    private String type;//玩法  '1':单式合买  '2':复式合买 
    private String typeValue;//玩法数值 只显示  '1','2'  2010-04-29 13:50新增
	private String zhDesc;//中文描述,给跟单人看
	private Long userid;//j金牌发起人 用户ID
    private String status;//状态    '1':审核通过,状态正常
    private String gdStatus;//定制自动跟单状态    '1':未满额   '2':已满额
    private Long dzCnt;//最大定制人数,此值从字典表里获取,修改此值时必须用同步方法
    private Long alreadyDzCnt;//已经定制的人数
    private String planDesc;//金牌发起人描述
    private int gdCnt;//总跟单人次
    private BigDecimal gdMoney;
   
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	public Long getBetMilitary() {
		return betMilitary;
	}
	public void setBetMilitary(Long betMilitary) {
		this.betMilitary = betMilitary;
	}
	
	public String getZhDesc() {
		return zhDesc;
	}
	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGdStatus() {
		return gdStatus;
	}
	public void setGdStatus(String gdStatus) {
		this.gdStatus = gdStatus;
	}
	public Long getDzCnt() {
		return dzCnt;
	}
	public void setDzCnt(Long dzCnt) {
		this.dzCnt = dzCnt;
	}
	public Long getAlreadyDzCnt() {
		return alreadyDzCnt;
	}
	public void setAlreadyDzCnt(Long alreadyDzCnt) {
		this.alreadyDzCnt = alreadyDzCnt;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	public int getGdCnt() {
		return gdCnt;
	}
	public void setGdCnt(int gdCnt) {
		this.gdCnt = gdCnt;
	}
	public BigDecimal getGdMoney() {
		return gdMoney;
	}
	public void setGdMoney(BigDecimal gdMoney) {
		this.gdMoney = gdMoney;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBetCategoryValue() {
		return betCategoryValue;
	}
	public void setBetCategoryValue(String betCategoryValue) {
		this.betCategoryValue = betCategoryValue;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}



}