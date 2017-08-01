package com.wintv.lottery.bet.vo;

public class KingSponsorVO {

    
	private Long id;//主键
	private String username;//金牌发起人用户名
	private String betCategory;//彩种
	private Long betMilitary;//投注战绩
    private String type;//玩法  '1':单式合买  '2':复式合买
	private String zhDesc;//中文描述,给跟单人看
	private Long userid;//j金牌发起人 用户ID
    private String status;//状态    '1':审核通过,状态正常
    private String gdStatus;//定制自动跟单状态    '1':未满额   '2':已满额
    private Long dzCnt;//最大定制人数,此值从字典表里获取,修改此值时必须用同步方法
    private Long alreadyDzCnt;//已经定制的人数
    private String planDesc;//金牌发起人描述
	public Long getAlreadyDzCnt() {
		return alreadyDzCnt;
	}


	public void setAlreadyDzCnt(Long alreadyDzCnt) {
		this.alreadyDzCnt = alreadyDzCnt;
	}


	public Long getDzCnt() {
		return dzCnt;
	}


	public void setDzCnt(Long dzCnt) {
		this.dzCnt = dzCnt;
	}


	public String getGdStatus() {
		return gdStatus;
	}


	public void setGdStatus(String gdStatus) {
		this.gdStatus = gdStatus;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	 

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZhDesc() {
		return this.zhDesc;
	}

	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getBetCategory() {
		return betCategory;
	}


	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}


	public String getPlanDesc() {
		return planDesc;
	}


	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}


	public Long getBetMilitary() {
		return betMilitary;
	}


	public void setBetMilitary(Long betMilitary) {
		this.betMilitary = betMilitary;
	}



}
