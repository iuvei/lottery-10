package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


@SuppressWarnings("serial")
public class PointTransactionLog implements Serializable {

	

	private Long logId;//主键
	private String txType;//日志类型
	private Long txValue;//交易积分数量
	private Date txTime;//交易时间
	private Long txUserId;//用户ID
	private Long allPoint;//剩余积分
	
	public PointTransactionLog() {
		
	}
	public PointTransactionLog(Long logId, String txType, Long txValue,
			Date txTime, Long txUserId, Long allPoint) {
	
		this.logId = logId;
		this.txType = txType;
		this.txValue = txValue;
		this.txTime = txTime;
		this.txUserId = txUserId;
		this.allPoint = allPoint;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public Long getTxValue() {
		return txValue;
	}
	public void setTxValue(Long txValue) {
		this.txValue = txValue;
	}
	public Date getTxTime() {
		return txTime;
	}
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	public Long getTxUserId() {
		return txUserId;
	}
	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}
	public Long getAllPoint() {
		return allPoint;
	}
	public void setAllPoint(Long allPoint) {
		this.allPoint = allPoint;
	}
	
}