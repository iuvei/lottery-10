package com.wintv.lottery.bet.vo;

public class MyAutoOrderVO {
	private Long autoOrderId;//主键
	private String kingUserName;//金牌发起人用户名
	private String username;//普通用户名
	private String category;
	private String type;//投注方式
	private String moneyRestrict;//金额限制
	private String subscribeMoney;//认购金额
	private String isLackOrder;//认购金额不足是否认购
	private long orderNum;//定制人序号(金牌发起人发起合买后，按照定制人序号依次自动跟单购买)
	private String orderTime;//定制时间
	public Long getAutoOrderId() {
		return autoOrderId;
	}
	public void setAutoOrderId(Long autoOrderId) {
		this.autoOrderId = autoOrderId;
	}
	public String getKingUserName() {
		return kingUserName;
	}
	public void setKingUserName(String kingUserName) {
		this.kingUserName = kingUserName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoneyRestrict() {
		return moneyRestrict;
	}
	public void setMoneyRestrict(String moneyRestrict) {
		this.moneyRestrict = moneyRestrict;
	}
	public String getSubscribeMoney() {
		return subscribeMoney;
	}
	public void setSubscribeMoney(String subscribeMoney) {
		this.subscribeMoney = subscribeMoney;
	}
	public String getIsLackOrder() {
		return isLackOrder;
	}
	public void setIsLackOrder(String isLackOrder) {
		this.isLackOrder = isLackOrder;
	}
	public long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	
	
	

}
