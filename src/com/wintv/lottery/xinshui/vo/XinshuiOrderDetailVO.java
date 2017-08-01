package com.wintv.lottery.xinshui.vo;

public class XinshuiOrderDetailVO {
	private String orderNo;//订单编号
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderUsername() {
		return orderUsername;
	}
	public void setOrderUsername(String orderUsername) {
		this.orderUsername = orderUsername;
	}
	public String getXinshuiNo() {
		return xinshuiNo;
	}
	public void setXinshuiNo(String xinshuiNo) {
		this.xinshuiNo = xinshuiNo;
	}
	public String getPubUsername() {
		return pubUsername;
	}
	public void setPubUsername(String pubUsername) {
		this.pubUsername = pubUsername;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEnsuremoney() {
		return ensuremoney;
	}
	public void setEnsuremoney(String ensuremoney) {
		this.ensuremoney = ensuremoney;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getContineuMoney() {
		return contineuMoney;
	}
	public void setContineuMoney(String contineuMoney) {
		this.contineuMoney = contineuMoney;
	}
	private String orderUsername;//订购人
	private String xinshuiNo;//心水编号
	private String pubUsername;//发布人
	private String hostName;//主队
	private String  guestName;//客队
	private String raceName;//联赛名
	private String startTime;//开赛时间
	private String ensuremoney;//保证金
	private String price;//价格
	private String type;//类型
	private Long orderId;//心水订单ID
	private String orderTime;//订购时间
	private String  contineuMoney;//需要继续支付的金额
}
