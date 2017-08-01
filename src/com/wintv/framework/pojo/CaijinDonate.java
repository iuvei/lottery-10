package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
/**
 * 彩金赠送记录
 *
 */
@SuppressWarnings("serial")
public class CaijinDonate implements Serializable {
    
	private Long id;
	private Date applyTime;
	private Long applyUserId;
	private String reason;
	private BigDecimal money;
	private BigDecimal allMoney;
	private Long auditUserId;
	private Date auditTime;
	private String status;
	private String orderNo;
    private String applyUser;
	private String auditUser;
	private Long donateNum;
	private String concreteReason;//详细理由
	private String auditReason;//审核理由
	private String deptCode;//部门
	private String deptName;//部门名称
	private String categoryType;//关联哪张订单表
	private String userList;//赠送用户名列表  用,隔开
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Long getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public Long getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public Long getDonateNum() {
		return donateNum;
	}
	public void setDonateNum(Long donateNum) {
		this.donateNum = donateNum;
	}
	public String getConcreteReason() {
		return concreteReason;
	}
	public void setConcreteReason(String concreteReason) {
		this.concreteReason = concreteReason;
	}
	public String getAuditReason() {
		return auditReason;
	}
	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}