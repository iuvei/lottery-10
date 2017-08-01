package com.wintv.lottery.mycenter.vo;

/*********
 * 所有未截止的投注记录
 * 
 * @author Arix04 2010-04-23
 * 
 */

public class BetRecordVO {
	
	private Long betId;				//主键
	private String betCategory;		//彩种
	private String phaseNo;			//期次号
	private String sponsorUsername; //投注人账号
	private Long sponsorBetId;		//投注人方案id
	private String allMoney;		//方案金额
	private String subscribeMoney;	//认购金额
	private String progress;		//进度
	private String planStatus;		//方案状态 '1':'满员'  '2':'未满员'  '3':'已撤单'
	private String orderStatus;		//订单状态 '1':未支付、'2':待出票、'3':已出票、'4':已取消、'5':已过期
	private String zjStatus;		//中奖状态 '1':'未开奖'  '2':'已返奖'  '3':'未中奖'
	private String bonus;			//我的奖金
	private String betTime;			//认购时间
	private String planCode;		//订单编号
	private Long alreadyBuyCopys;	//已购买份数
	private Long divideCopys;		//总份数
	private Long floorCopys;		//保底份数
	
	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public String getSponsorUsername() {
		return sponsorUsername;
	}

	public void setSponsorUsername(String sponsorUsername) {
		this.sponsorUsername = sponsorUsername;
	}

	public Long getSponsorBetId() {
		return sponsorBetId;
	}

	public void setSponsorBetId(Long sponsorBetId) {
		this.sponsorBetId = sponsorBetId;
	}

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public String getSubscribeMoney() {
		return subscribeMoney;
	}

	public void setSubscribeMoney(String subscribeMoney) {
		this.subscribeMoney = subscribeMoney;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getZjStatus() {
		return zjStatus;
	}

	public void setZjStatus(String zjStatus) {
		this.zjStatus = zjStatus;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getBetTime() {
		return betTime;
	}

	public void setBetTime(String betTime) {
		this.betTime = betTime;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Long getAlreadyBuyCopys() {
		return alreadyBuyCopys;
	}

	public void setAlreadyBuyCopys(Long alreadyBuyCopys) {
		this.alreadyBuyCopys = alreadyBuyCopys;
	}

	public Long getDivideCopys() {
		return divideCopys;
	}

	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}

	public Long getFloorCopys() {
		return floorCopys;
	}

	public void setFloorCopys(Long floorCopys) {
		this.floorCopys = floorCopys;
	}
	
}
