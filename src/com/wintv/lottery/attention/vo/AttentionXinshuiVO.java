package com.wintv.lottery.attention.vo;

public class AttentionXinshuiVO {
	private Long productId;//c2c主键
	private Long soldUserId;//民间高手用户ID
    private String price;//心水价格
	private String hostName;//主队名称
	private String guestName;//客队名称
	private String xinshuiNo;//心水编号
	private String raceName;//联赛名称
	private String soldUserName;//民间高手用户名
	private String startTime;//开赛时间
	private String ensureMoney;// 缴纳的保证金
	private int soldCnt;//购买人数
	private String winRatio;//最近5场胜率
	
    public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSoldUserId() {
		return soldUserId;
	}
	public void setSoldUserId(Long soldUserId) {
		this.soldUserId = soldUserId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getXinshuiNo() {
		return xinshuiNo;
	}
	public void setXinshuiNo(String xinshuiNo) {
		this.xinshuiNo = xinshuiNo;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public String getSoldUserName() {
		return soldUserName;
	}
	public void setSoldUserName(String soldUserName) {
		this.soldUserName = soldUserName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEnsureMoney() {
		return ensureMoney;
	}
	public void setEnsureMoney(String ensureMoney) {
		this.ensureMoney = ensureMoney;
	}
	public int getSoldCnt() {
		return soldCnt;
	}
	public void setSoldCnt(int soldCnt) {
		this.soldCnt = soldCnt;
	}
	public String getWinRatio() {
		return winRatio;
	}
	public void setWinRatio(String winRatio) {
		this.winRatio = winRatio;
	}
	
}
