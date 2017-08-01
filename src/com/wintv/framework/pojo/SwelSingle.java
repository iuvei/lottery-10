package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


@SuppressWarnings("serial")
public class SwelSingle implements Serializable {

	// Fields

	private Long swlSingleId;
	private Long betUserid;
	private String phase;
	private Long scene;
	private String wel;
	private Long txMoney;
	private String plan;
	private Date betDate;

	// Constructors

	/** default constructor */
	public SwelSingle() {
	}

	/** full constructor */
	public SwelSingle(Long betUserid, String phase, Long scene,
			String wel, Long txMoney, String plan, Date betDate) {
		this.betUserid = betUserid;
		this.phase = phase;
		this.scene = scene;
		this.wel = wel;
		this.txMoney = txMoney;
		this.plan = plan;
		this.betDate = betDate;
	}

	// Property accessors

	public Long getSwlSingleId() {
		return this.swlSingleId;
	}

	public void setSwlSingleId(Long swlSingleId) {
		this.swlSingleId = swlSingleId;
	}

	public Long getBetUserid() {
		return this.betUserid;
	}

	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}

	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Long getScene() {
		return this.scene;
	}

	public void setScene(Long scene) {
		this.scene = scene;
	}

	public String getWel() {
		return this.wel;
	}

	public void setWel(String wel) {
		this.wel = wel;
	}

	public Long getTxMoney() {
		return this.txMoney;
	}

	public void setTxMoney(Long txMoney) {
		this.txMoney = txMoney;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Date getBetDate() {
		return this.betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

}