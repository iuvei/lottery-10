package com.wintv.lottery.bet.vo;


public class BetOrderVO {
	private String txtPath;
	private Long betId;//主键
	private String wiciType;//过关类型
	private String isDingdan;//是否定胆
	private String viciWay;//过关方式
	private Long betNumber;//注数
	private String phaseNo;//期次号
	private String phaseId;//期次ID
	private String betTime;//投注时间
	private Long betUserid;//投注人用户ID
	private Long betMulti;//倍数
	private String plan;//方案(胜胜 33 ..其他类似)
	private String type;//类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	private Long sponsorUserid;//发起人用户ID
	private String isFloor;//是否保底
	private String planCode;//方案编号
	private String formatStr;//格式转换字符
	private String singleMoney;//单倍金额
	private String allMoney;//总金额
	private Long divideCopys;//分成份数
	private java.math.BigDecimal floorCopys;//保底份数
	private String isOpen;//是否公开
	private String recruitUsers;//招股对象
	private String orderNo;//订单号
	private String category;//彩种
	private String useCaijin;//是否使用彩金 1:使用  2：不使用
	private String bgColor;//颜色
	private String betUsername;
	private String betCategory;
	private String endTime;
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public String getUseCaijin() {
		return useCaijin;
	}
	public void setUseCaijin(String useCaijin) {
		this.useCaijin = useCaijin;
	}
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
	public Long getSponsorUserid() {
		return sponsorUserid;
	}
	public void setSponsorUserid(Long sponsorUserid) {
		this.sponsorUserid = sponsorUserid;
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
	
	public Long getDivideCopys() {
		return divideCopys;
	}
	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}
	public java.math.BigDecimal getFloorCopys() {
		return floorCopys;
	}
	public void setFloorCopys(java.math.BigDecimal floorCopys) {
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBetUsername() {
		return betUsername;
	}
	public void setBetUsername(String betUsername) {
		this.betUsername = betUsername;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
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
	public String getTxtPath() {
		return txtPath;
	}
	public void setTxtPath(String txtPath) {
		this.txtPath = txtPath;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
}
