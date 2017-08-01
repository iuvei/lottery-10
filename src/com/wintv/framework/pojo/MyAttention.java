package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MyAttention implements Serializable {
    private Long attentionId;//主键
	private Date attentionTime;//关注时间
	private Long userId;//用户ID
	private Long targetUserId;//被关注的彩种  或者  被关注人用户ID
    private String username;//用户名(主动)
    private String targetUsername;//被关注的用户名
	public Long getAttentionId() {
		return attentionId;
	}
	public void setAttentionId(Long attentionId) {
		this.attentionId = attentionId;
	}
	public Date getAttentionTime() {
		return attentionTime;
	}
	public void setAttentionTime(Date attentionTime) {
		this.attentionTime = attentionTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getTargetUsername() {
		return targetUsername;
	}
	public void setTargetUsername(String targetUsername) {
		this.targetUsername = targetUsername;
	}
 
}