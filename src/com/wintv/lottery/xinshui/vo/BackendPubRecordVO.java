package com.wintv.lottery.xinshui.vo;

public class BackendPubRecordVO {
	private Long id;
	private String orderNo;//订单编号
	private String orderUsername;//订购人
	private String xinshuiNo;//心水编号
	private String pubUsername;//发布人
	private String hotname;//主队
	private String  guestName;//客队
	private String raceName;//联赛名
	private String startTime;//开赛时间
	private String ensuremoney;//保证金
	private String price;//价格
	private String type;//类型
	private Long orderCnt;//购买人数
	private String orderTime;//订购时间
	private String pubTime;//发布时间
	private String status;//状态
	private Long raceId;
	private Long againstId;//对阵ID
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
	public String getHotname() {
		return hotname;
	}
	public void setHotname(String hotname) {
		this.hotname = hotname;
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
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(Long orderCnt) {
		this.orderCnt = orderCnt;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRaceId() {
		return raceId;
	}
	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	

}
