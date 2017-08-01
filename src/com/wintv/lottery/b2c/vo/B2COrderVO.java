package com.wintv.lottery.b2c.vo;

public class B2COrderVO {
	private String orderNo;//购买心水时产生的订单编号,对应表T_XINSHUI_ORDER表的ORDER_NO
	private String soldUsername;//发布人
	private String hostName;
	private String startTime;
	private String guestName;
	private String raceName;
	private String b2cProductNO;//发布心水时 产生的心水编号,对应表T_C2C_PRODUCT的字段XINSHUI_NO
	private String price;//心水价格
	private String orderTime;//心水订购时间
	private String payStatus;//支付状态 支付状态:，1:“未支付”、2:“已支付”、3:“已扣款”、4:“已赔付”   5:“ 已取消”
	private String status;//心水状态:    "1":发布中   "2":赢       "3":负  "4":走    "5":已关闭
	private String type;//'1':亚盘   '2':大小盘
	private String orderUsername;//订购人用户名
	private Long buyCnt;//销售量
	private String pubTime;//
	private Long xinshuiId;//心水ID 对应T_C2C_PRODUCT表的主键
	private Long xinshuiOrderId;//心水订单ID
	private String orderType;//订购类型
	private String endTime;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSoldUsername() {
		return soldUsername;
	}
	public void setSoldUsername(String soldUsername) {
		this.soldUsername = soldUsername;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
	public String getB2cProductNO() {
		return b2cProductNO;
	}
	public void setB2cProductNO(String productNO) {
		b2cProductNO = productNO;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderUsername() {
		return orderUsername;
	}
	public void setOrderUsername(String orderUsername) {
		this.orderUsername = orderUsername;
	}
	public Long getBuyCnt() {
		return buyCnt;
	}
	public void setBuyCnt(Long buyCnt) {
		this.buyCnt = buyCnt;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public Long getXinshuiId() {
		return xinshuiId;
	}
	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}
	public Long getXinshuiOrderId() {
		return xinshuiOrderId;
	}
	public void setXinshuiOrderId(Long xinshuiOrderId) {
		this.xinshuiOrderId = xinshuiOrderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
