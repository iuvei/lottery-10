package com.wintv.framework.pojo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Bank implements Serializable {

	

	private Long id;
	private String name;
	private String img;
	private String url;
	private String chargeStatus;
	private String code;
	private String withdrawStatus;

	public Bank() {
	}

	
	public Bank(String code) {
		this.code = code;
	}

	
	public Bank(String name, String img, String url,
			String chargeStatus, String code, String withdrawStatus) {
		this.name = name;
		this.img = img;
		this.url = url;
		this.chargeStatus = chargeStatus;
		this.code = code;
		this.withdrawStatus = withdrawStatus;
	}

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChargeStatus() {
		return this.chargeStatus;
	}

	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWithdrawStatus() {
		return this.withdrawStatus;
	}

	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

}