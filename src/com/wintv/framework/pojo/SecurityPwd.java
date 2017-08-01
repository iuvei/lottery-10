package com.wintv.framework.pojo;

import java.util.Date;


@SuppressWarnings("serial")
public class SecurityPwd implements java.io.Serializable {

	

	private Long securityId;
	private Long userId;
	private Date txTime;
	private Long cnt;

	
	public SecurityPwd() {
	}

	
	public SecurityPwd(Long cnt) {
		this.cnt = cnt;
	}

	
	public SecurityPwd(Long userId, Date txTime, Long cnt) {
		this.userId = userId;
		this.txTime = txTime;
		this.cnt = cnt;
	}

	

	public Long getSecurityId() {
		return this.securityId;
	}

	public void setSecurityId(Long securityId) {
		this.securityId = securityId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getTxTime() {
		return this.txTime;
	}

	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}

	public Long getCnt() {
		return this.cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

}