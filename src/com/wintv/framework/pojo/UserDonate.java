package com.wintv.framework.pojo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class UserDonate implements Serializable {

	private Long udId;
	private Long donateId;
	private Long userId;
	private Double txCaijin;
	private Double allCaijin;

	
	public UserDonate() {
	}
    public UserDonate(Long donateId, Long userId, Double txCaijin,
			Double allCaijin) {
		this.donateId = donateId;
		this.userId = userId;
		this.txCaijin = txCaijin;
		this.allCaijin = allCaijin;
	}

	 

	public Long getUdId() {
		return this.udId;
	}

	public void setUdId(Long udId) {
		this.udId = udId;
	}

	public Long getDonateId() {
		return this.donateId;
	}

	public void setDonateId(Long donateId) {
		this.donateId = donateId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getTxCaijin() {
		return this.txCaijin;
	}

	public void setTxCaijin(Double txCaijin) {
		this.txCaijin = txCaijin;
	}

	public Double getAllCaijin() {
		return this.allCaijin;
	}

	public void setAllCaijin(Double allCaijin) {
		this.allCaijin = allCaijin;
	}

}