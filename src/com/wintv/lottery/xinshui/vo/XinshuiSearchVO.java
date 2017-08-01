package com.wintv.lottery.xinshui.vo;

import java.io.Serializable;
import java.math.BigDecimal;
@SuppressWarnings("serial")
public class XinshuiSearchVO  implements Serializable{
	private Long c2cId;
	private String raceName;
	private String type;
	private String startTime;
	private String hostName;
	private String guestName;
	private String username;
	private int xinshuiMilitary;//战绩
	private String winRatio;
	private BigDecimal ensureMoney;//缴纳的保证金
	private Long cnt;//购买人数
	private BigDecimal price;//心水价格
	private String aindex;// 主队或者大球指数
	private String bindex;// 客队或者小球指数
	private String panKouIndex;// 盘口
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getWinRatio() {
		return winRatio;
	}
	public void setWinRatio(String winRatio) {
		this.winRatio = winRatio;
	}
	public BigDecimal getEnsureMoney() {
		return ensureMoney;
	}
	public void setEnsureMoney(BigDecimal ensureMoney) {
		this.ensureMoney = ensureMoney;
	}
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getC2cId() {
		return c2cId;
	}
	public void setC2cId(Long id) {
		c2cId = id;
	}
	public int getXinshuiMilitary() {
		return xinshuiMilitary;
	}
	public void setXinshuiMilitary(int xinshuiMilitary) {
		this.xinshuiMilitary = xinshuiMilitary;
	}

	public String getPanKouIndex() {
		return panKouIndex;
	}
	public void setPanKouIndex(String panKouIndex) {
		this.panKouIndex = panKouIndex;
	}
	public String getAindex() {
		return aindex;
	}
	public void setAindex(String aindex) {
		this.aindex = aindex;
	}
	public String getBindex() {
		return bindex;
	}
	public void setBindex(String bindex) {
		this.bindex = bindex;
	}
	
	

}
