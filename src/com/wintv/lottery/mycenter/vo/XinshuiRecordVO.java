package com.wintv.lottery.mycenter.vo;

import java.util.Date;

/*********
 * 所有未截止的心水记录
 * 
 * @author Arix04 2010-04-23
 * 
 */

public class XinshuiRecordVO {
	
	private Long xinshuiId;			//心水ID
	private Long orderId;			//心水订单ID
	private String orderNo;			//心水订单NO
	private Long pubUserid;			//发布人id
	private String pubUsername;		//发布人名
	private String raceName;		//赛事
	private String hostName;		//主队名
	private String guestName;		//客队名
	private String startTime;		//开赛时间，格式'yy-mm-dd hh24:mi'
	private String xinshuiNo;		//心水NO
	private String ensureMoney;		//保证金
	private String price;			//心水价格
	private String buyTime;			//购买时间，格式'yy-mm-dd hh24:mi'
	private String payStatus;		//支付状态
	private String xinshuiStatus;	//心水状态

	public Long getXinshuiId() {
		return xinshuiId;
	}

	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPubUsername() {
		return pubUsername;
	}

	public void setPubUsername(String pubUsername) {
		this.pubUsername = pubUsername;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getXinshuiNo() {
		return xinshuiNo;
	}

	public void setXinshuiNo(String xinshuiNo) {
		this.xinshuiNo = xinshuiNo;
	}

	public String getEnsureMoney() {
		return ensureMoney;
	}

	public void setEnsureMoney(String ensureMoney) {
		this.ensureMoney = ensureMoney;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getXinshuiStatus() {
		return xinshuiStatus;
	}

	public void setXinshuiStatus(String xinshuiStatus) {
		this.xinshuiStatus = xinshuiStatus;
	}

	public Long getPubUserid() {
		return pubUserid;
	}

	public void setPubUserid(Long pubUserid) {
		this.pubUserid = pubUserid;
	}

}
