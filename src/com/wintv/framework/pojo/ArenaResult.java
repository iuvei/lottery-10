package com.wintv.framework.pojo;

import java.lang.Long;

/**
 * AbstractTArenaResult entity provides the base persistence definition of the
 * TArenaResult entity. @author MyEclipse Persistence Tools
 */

public class ArenaResult implements java.io.Serializable {

	// Fields

	private Long arId;
	private Long betUserid;

	// Constructors

	/** default constructor */
	public ArenaResult() {
	}

	/** full constructor */
	public ArenaResult(Long betUserid) {
		this.betUserid = betUserid;
	}

	// Property accessors

	public Long getArId() {
		return this.arId;
	}

	public void setArId(Long arId) {
		this.arId = arId;
	}

	public Long getBetUserid() {
		return this.betUserid;
	}

	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}

}