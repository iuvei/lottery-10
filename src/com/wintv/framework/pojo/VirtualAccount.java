package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.math.BigDecimal;

/**
 * 用户的金额虚拟账号
 * 
 * 
 */
@SuppressWarnings("serial")
public class VirtualAccount implements Serializable {

	private Long vaId;
	private Long txUserId;
	private String status;
	private BigDecimal allMoney;
	private BigDecimal frozenMoney;
	private BigDecimal mosaicGold;
	private Long point;
	private String bankCode;
	private String cardNum;
	private String province;
	private String city;
	private BigDecimal frozenMosaicGold;
	private String branch;
	private String bankName;
	private String userName;
	private String name;//真实姓名
	public Long getVaId() {
		return vaId;
	}
	public void setVaId(Long vaId) {
		this.vaId = vaId;
	}
	public Long getTxUserId() {
		return txUserId;
	}
	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public BigDecimal getMosaicGold() {
		return mosaicGold;
	}
	public void setMosaicGold(BigDecimal mosaicGold) {
		this.mosaicGold = mosaicGold;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(Long point) {
		this.point = point;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public BigDecimal getFrozenMosaicGold() {
		return frozenMosaicGold;
	}
	public void setFrozenMosaicGold(BigDecimal frozenMosaicGold) {
		this.frozenMosaicGold = frozenMosaicGold;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}