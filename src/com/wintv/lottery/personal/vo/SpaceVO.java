package com.wintv.lottery.personal.vo;

import java.util.List;

public class SpaceVO {
	private List<BonusVO> latestBonusList;//最新中奖
	private String bonusList;//获奖记录
	private long userGrade;//用户等级
	private String regTime;//注册时间
    private long attentedCnt;//被关注次数
    private long attentCnt;//他关注别人次数
    private String username;//用户名
    private long betMilitary;//他的购彩战绩
    private long xinshuiMilitary;//他的心水战绩
    private long spCnt;//发起合买数
    private String spSuccess;//发单成功率
    private long canYuCn;//参与合买次数
    private long zjCnt;//总中奖次数 
    private long pubXinshui;//3发布心水数 
    private long soldXinshuiCnt;//4销售心水人数
    private List<KingInfo>  kingList;//
    private List<LastVisitedVO>  lastVisitedList;//最近访客
    private String signature;//个人中心的个人签名
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public long getAttentedCnt() {
		return attentedCnt;
	}
	public void setAttentedCnt(long attentedCnt) {
		this.attentedCnt = attentedCnt;
	}
	public long getAttentCnt() {
		return attentCnt;
	}
	public void setAttentCnt(long attentCnt) {
		this.attentCnt = attentCnt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getBetMilitary() {
		return betMilitary;
	}
	public void setBetMilitary(long betMilitary) {
		this.betMilitary = betMilitary;
	}
	public long getXinshuiMilitary() {
		return xinshuiMilitary;
	}
	public void setXinshuiMilitary(long xinshuiMilitary) {
		this.xinshuiMilitary = xinshuiMilitary;
	}
	public long getSpCnt() {
		return spCnt;
	}
	public void setSpCnt(long spCnt) {
		this.spCnt = spCnt;
	}
	public String getSpSuccess() {
		return spSuccess;
	}
	public void setSpSuccess(String spSuccess) {
		this.spSuccess = spSuccess;
	}
	public long getCanYuCn() {
		return canYuCn;
	}
	public void setCanYuCn(long canYuCn) {
		this.canYuCn = canYuCn;
	}
	public long getZjCnt() {
		return zjCnt;
	}
	public void setZjCnt(long zjCnt) {
		this.zjCnt = zjCnt;
	}
	public long getPubXinshui() {
		return pubXinshui;
	}
	public void setPubXinshui(long pubXinshui) {
		this.pubXinshui = pubXinshui;
	}
	public long getSoldXinshuiCnt() {
		return soldXinshuiCnt;
	}
	public void setSoldXinshuiCnt(long soldXinshuiCnt) {
		this.soldXinshuiCnt = soldXinshuiCnt;
	}
	public List<KingInfo> getKingList() {
		return kingList;
	}
	public void setKingList(List<KingInfo> kingList) {
		this.kingList = kingList;
	}
	public List<LastVisitedVO> getLastVisitedList() {
		return lastVisitedList;
	}
	public void setLastVisitedList(List<LastVisitedVO> lastVisitedList) {
		this.lastVisitedList = lastVisitedList;
	}
	public long getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(long userGrade) {
		this.userGrade = userGrade;
	}
	public String getBonusList() {
		return bonusList;
	}
	public void setBonusList(String bonusList) {
		this.bonusList = bonusList;
	}
	public List<BonusVO> getLatestBonusList() {
		return latestBonusList;
	}
	public void setLatestBonusList(List<BonusVO> latestBonusList) {
		this.latestBonusList = latestBonusList;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
