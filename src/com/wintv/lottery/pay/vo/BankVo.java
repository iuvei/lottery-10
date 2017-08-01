package com.wintv.lottery.pay.vo;

import java.math.BigDecimal;

/**
 * @author Arix04
 * 
 * @version 1.0.0
 */
public class BankVo {

	private String bankName;// 银行名称
	private String bankCode;// 银行代码
	private String imgUrl;// 图片url
	private String bankUrl;// 银行url

	public BankVo() {
		// TODO Auto-generated constructor stub
	}

	public BankVo(String bankName, String bankCode, String imgUrl,
			String bankUrl) {
		super();
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.imgUrl = imgUrl;
		this.bankUrl = bankUrl;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getBankUrl() {
		return bankUrl;
	}

	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}

}
