package com.wintv.lottery.attention.vo;

public class AttentionUserVO {
	private Long attentionId;//主键
	private String username;//用户名
	private Long userId;
	private String title;//头衔
	private int betMilitary;//代购合买战绩
	private int xinshuiMilitary;//心水战绩
	private Long targetUserId;//被关在的人用户ID
	private String isAutoOrder;//是否定制自动跟单
	private int participateCnt;//参与我的方案次数
	private int buyMyXinshuiCnt;//购买我的心水次数
	private long attentionCnt;//关注人数
	private long rownum;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBetMilitary() {
		return betMilitary;
	}
	public void setBetMilitary(int betMilitary) {
		this.betMilitary = betMilitary;
	}
	public int getXinshuiMilitary() {
		return xinshuiMilitary;
	}
	public void setXinshuiMilitary(int xinshuiMilitary) {
		this.xinshuiMilitary = xinshuiMilitary;
	}
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getIsAutoOrder() {
		return isAutoOrder;
	}
	public void setIsAutoOrder(String isAutoOrder) {
		this.isAutoOrder = isAutoOrder;
	}
	public int getParticipateCnt() {
		return participateCnt;
	}
	public void setParticipateCnt(int participateCnt) {
		this.participateCnt = participateCnt;
	}
	public int getBuyMyXinshuiCnt() {
		return buyMyXinshuiCnt;
	}
	public void setBuyMyXinshuiCnt(int buyMyXinshuiCnt) {
		this.buyMyXinshuiCnt = buyMyXinshuiCnt;
	}
	public Long getAttentionId() {
		return attentionId;
	}
	public void setAttentionId(Long attentionId) {
		this.attentionId = attentionId;
	}
	public long getAttentionCnt() {
		return attentionCnt;
	}
	public void setAttentionCnt(long attentionCnt) {
		this.attentionCnt = attentionCnt;
	}
	public long getRownum() {
		return rownum;
	}
	public void setRownum(long rownum) {
		this.rownum = rownum;
	}
	
}
