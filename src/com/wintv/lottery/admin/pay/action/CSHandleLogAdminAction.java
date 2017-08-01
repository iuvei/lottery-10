package com.wintv.lottery.admin.pay.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.lottery.pay.service.PayService;
/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class CSHandleLogAdminAction extends BaseAction{
	private static final long serialVersionUID = -8220586128606202214L;
	private Long logId;
	private String logType;
	private String opType;
	@Autowired
	private PayService payService;
	
	public String excute() {
		return SUCCESS;
	}
	/**
	 * 获取客服操作日志列表
	 */
	public String getCSHandleLogList() {
		try {
			List<CSHandleLog> result = null;
			if (isNotNull(this.logId,this.logType)) {
				result = payService.findCsHandleLogListAdmin(logId,logType);
			}
			if(null == result){
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
}
