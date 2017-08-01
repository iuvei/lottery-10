package com.wintv.framework.pojo;
// default package

import java.util.Date;

/**
 * SecurityIpId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SecurityIp implements java.io.Serializable {

	// Fields

	private Long securityId;
	private Long userId;
	private String ip;
	private Date txTime;
	private Long cnt;

	// Constructors

	/** default constructor */
	public SecurityIp() {
	}

	/** minimal constructor */
	public SecurityIp(Long securityId, String ip, Date txTime, Long cnt) {
		this.securityId = securityId;
		this.ip = ip;
		this.txTime = txTime;
		this.cnt = cnt;
	}

	/** full constructor */
	public SecurityIp(Long securityId, Long userId, String ip, Date txTime,
			Long cnt) {
		this.securityId = securityId;
		this.userId = userId;
		this.ip = ip;
		this.txTime = txTime;
		this.cnt = cnt;
	}

	// Property accessors

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

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getTxTime() {
		return this.txTime;
	}

	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}


}