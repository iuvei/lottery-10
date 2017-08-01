package com.wintv.lottery.xinshui.vo;

/**
 * 
 * @author Administrator
 *
 */
public class XinshuiDetailVO {
	private String xinshuiNo;//心水编号(与订单号类似)
	private String txUsername;//民间高手用户名
	private String startTime;//开赛时间
	private String raceName;//赛事名称
	private String ensureMoney;//缴纳的保证金
	private String price;//心水价格
	private String hostName;
	private String guestName;

	private String hostWinEu;//欧盘主胜
	private String drawEu;///欧盘平局
	private String hostFailEu;//欧盘主负
	
	
	private String hostWinAsia;//亚盘主胜
	private String drawAsia;///亚盘平局
	private String hostFailAsia;//亚盘主负
	private String pubTime;//发布时间
	
	private String hostWinBigSmall;//大小球主胜
	private String drawBigSmall;///大小球平局
	private String hostFailBigSmall;//大小球主负
	
	private Long orderCnt;//购买人数
	private String status;
	private String zhDesc;//心水说明
	private String selectType;//选主 1：选主  2：选客 3：选大 4：选小
	private String hostImg;
	private String guestImg;
	public String getXinshuiNo() {
		return xinshuiNo;
	}
	public void setXinshuiNo(String xinshuiNo) {
		this.xinshuiNo = xinshuiNo;
	}
	public String getTxUsername() {
		return txUsername;
	}
	public void setTxUsername(String txUsername) {
		this.txUsername = txUsername;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
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
	public String getHostWinEu() {
		return hostWinEu;
	}
	public void setHostWinEu(String hostWinEu) {
		this.hostWinEu = hostWinEu;
	}
	public String getDrawEu() {
		return drawEu;
	}
	public void setDrawEu(String drawEu) {
		this.drawEu = drawEu;
	}
	public String getHostFailEu() {
		return hostFailEu;
	}
	public void setHostFailEu(String hostFailEu) {
		this.hostFailEu = hostFailEu;
	}
	public String getHostWinAsia() {
		return hostWinAsia;
	}
	public void setHostWinAsia(String hostWinAsia) {
		this.hostWinAsia = hostWinAsia;
	}
	public String getDrawAsia() {
		return drawAsia;
	}
	public void setDrawAsia(String drawAsia) {
		this.drawAsia = drawAsia;
	}
	public String getHostFailAsia() {
		return hostFailAsia;
	}
	public void setHostFailAsia(String hostFailAsia) {
		this.hostFailAsia = hostFailAsia;
	}
	public String getHostWinBigSmall() {
		return hostWinBigSmall;
	}
	public void setHostWinBigSmall(String hostWinBigSmall) {
		this.hostWinBigSmall = hostWinBigSmall;
	}
	public String getDrawBigSmall() {
		return drawBigSmall;
	}
	public void setDrawBigSmall(String drawBigSmall) {
		this.drawBigSmall = drawBigSmall;
	}
	public String getHostFailBigSmall() {
		return hostFailBigSmall;
	}
	public void setHostFailBigSmall(String hostFailBigSmall) {
		this.hostFailBigSmall = hostFailBigSmall;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public Long getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(Long orderCnt) {
		this.orderCnt = orderCnt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZhDesc() {
		return zhDesc;
	}
	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getHostImg() {
		return hostImg;
	}
	public void setHostImg(String hostImg) {
		this.hostImg = hostImg;
	}
	public String getGuestImg() {
		return guestImg;
	}
	public void setGuestImg(String guestImg) {
		this.guestImg = guestImg;
	}
	
	
	
}
