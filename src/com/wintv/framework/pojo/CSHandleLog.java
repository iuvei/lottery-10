package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 客服处理日志,万一出了什么事情 可以立即找到当事人
 *
 */
@SuppressWarnings("serial")
public class CSHandleLog implements Serializable {
    private Long id;//主键
	private Long logId;//外键 关联日志表(T_WITHDRAW_LOG或T_VA_FROZEN_LOG)
	private Long csUserId;//客服用户ID
	
	private Date handleTime;//客服处理时间
	private String csName;//客服姓名
	private String memo;//客服处理结果
	private String type;//'1':T_WITHDRAW_LOG        '2':T_VA_FROZEN_LOG
	private String opType;//'1':受理取款 '2':转账 3:撤销转账
	private String ip;//客服机器的IP
    public CSHandleLog() {
	}
    public CSHandleLog(Long csUserId, Date handleTime,String csName,String ip) {
		this.csUserId = csUserId;
		this.handleTime = handleTime;
		this.csName = csName;
		this.ip=ip;
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getCsUserId() {
		return this.csUserId;
	}

	public void setCsUserId(Long csUserId) {
		this.csUserId = csUserId;
	}

	public Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getCsName() {
		return this.csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}