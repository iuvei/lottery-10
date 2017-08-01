package com.wintv.lottery.admin.bet.vo;

public class KingAdminSponsorVO {
	private Long id;//主键
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
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public int getDzCnt() {
		return dzCnt;
	}
	public void setDzCnt(int dzCnt) {
		this.dzCnt = dzCnt;
	}
	public int getAlreadyDzCnt() {
		return alreadyDzCnt;
	}
	public void setAlreadyDzCnt(int alreadyDzCnt) {
		this.alreadyDzCnt = alreadyDzCnt;
	}
	private String username;//金牌发起人用户名
	private String betCategory;//彩种
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	private String type;//玩法  '1':单式合买  '2':复式合买
	private String zhDesc;//中文描述,给跟单人看
	private Long userid;//j金牌发起人 用户ID
    private String status;//状态    '1':审核通过,状态正常
    private String gdStatus;//定制自动跟单状态    '1':未满额   '2':已满额
    private int dzCnt;//最大定制人数,此值从字典表里获取
    private int alreadyDzCnt;//已定制
	private String betMilitary;//投注战绩
	private Long allGdCnt;//总跟单人次
	private String allGdMoney;//跟单总金

	public String getAllGdMoney() {
		return allGdMoney;
	}
	public void setAllGdMoney(String allGdMoney) {
		this.allGdMoney = allGdMoney;
	}
	public Long getAllGdCnt() {
		return allGdCnt;
	}
	public void setAllGdCnt(Long allGdCnt) {
		this.allGdCnt = allGdCnt;
	}
	public String getBetMilitary() {
		return betMilitary;
	}


	public void setBetMilitary(String betMilitary) {
		this.betMilitary = betMilitary;
	}

}
