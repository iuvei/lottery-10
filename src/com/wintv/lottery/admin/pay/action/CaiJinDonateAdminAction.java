package com.wintv.lottery.admin.pay.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.CaijinDonate;
import com.wintv.framework.utils.Util;
import com.wintv.lottery.pay.service.PayService;

/**
 * 彩金系统后台管理
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class CaiJinDonateAdminAction extends BaseAction {
	private static final long serialVersionUID = -3149132858080613568L;
	private Long caiJinLogId;// 彩金订单记录Id
	private Long applyUserId;// 申请人用户Id
	private Long auditUserId;// 审核人用户Id
	private String caiJinBeginTime = "2010-01-12";// 申请时间查询区间开始时间
													// 格式:"yyyy-MM-dd"
	private String caiJinEndTime = "2010-01-18";// 申请时间查询区间结束时间 格式:"yyyy-MM-dd"

	private String auditBeginTime = "2010-01-12";// 审核时间查询区间开始时间
													// 格式:"yyyy-MM-dd"
	private String auditEndTime = "2010-01-18";// 审核时间查询区间结束时间 格式:"yyyy-MM-dd"

	private String caiJinStatus = "0";// 订单状态 1：未审核 2.已审核 3.已撤销

	private String caiJinMoneyMin;// 合计金额查询区间 最小金额 格式:"123.00"
	private String caiJinMoneyMax;// 合计金额查询区间 最大金额 格式:"123.00"

	private String queryFiled = "userName";// 检索关键字段 订单号、申请理由
	private String queryFileValue = "";// 检索关键字段值

	private Integer orderTypeIndex = 0;// 排序类型索引 0,1,2,3,4,5
	private Integer pageSize = 20;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private String isCommit = "no";// 是否提交 客服填写备注说明
	private String logType = Constants.ORDER_CATEGORY_MOSAIC_GOLD_ZS;
	private String csMemo = "备注";// 客服填写的记录内容
	// 彩金添加页面参数
	private String reason = "";// 简短理由
	private String concreteReason = "";// 详细理由
	private String userNames = "";// 赠送的用户名，逗号分隔
	private BigDecimal caijinMoney = new BigDecimal(0);// 每用户/赠送金额
	private String deptCode;// 部门
	private String deptName;//部门名称
	private String applyUserName;// 申请人用户名

	private static final String[][] ORDER_TYPE_CONFIG = {
			{ "apply_time", "desc" },// 申请时间由近到远
			{ "apply_time", "asc" },// 申请时间由远到近
			{ "audit_time", "desc" },// 审核时间由近到远
			{ "audit_time", "asc" },// 审核时间由远到近
			{ "all_money", "asc" },// 合计金额由低到高
			{ "all_money", "desc" } };// 合计金额由高到低

	@Autowired
	private PayService payService;

	public String excute() {
		return SUCCESS;
	}

	/**
	 * 彩金系统后台 -- 查询彩金捐赠列表
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @param orderFiled
	 *            排序字段
	 * @param orderType
	 *            排序类型 “ASC|DESC”
	 * @param startRow
	 *            查询开始行
	 * @param pageSize
	 *            页面记录数大小
	 * @return 返回查询记录列表
	 * @throws DaoException
	 */
	public String findCaiJinDonateList() {
		try {
			String orderFiled = "";
			String orderType = "";
			if (isNotNull(this.orderTypeIndex)
					&& this.orderTypeIndex < ORDER_TYPE_CONFIG.length) {
				orderFiled = ORDER_TYPE_CONFIG[orderTypeIndex][0];
				orderType = ORDER_TYPE_CONFIG[orderTypeIndex][1];
			}
			// 根据页面参数生成查询条件
			StringBuilder queryCondition = generateQueryCondition();
			List<CaijinDonate> caiJinLogs = payService
					.findCaiJinDonateListAdmin(queryCondition, orderFiled,
							orderType, startRow, pageSize);
			Map<String, Object> total = new HashMap<String, Object>();
			if (this.isCount == 1) {
				total = payService.countCaiJinDonateListAdmin(queryCondition);
			}
			if (isNotNull(caiJinLogs, total)) {
				Map result = new HashMap();
				result.put("totalCount", total.get("totalCount") == null ? 0
						: total.get("totalCount"));
				result.put("totalMoney", total.get("totalMoney") == null ? 0
						: total.get("totalMoney"));
				result.put("caiJinLogs", caiJinLogs);
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 彩金系统后台-获取某条彩金系统详情
	 * 
	 * 
	 * @param caiJinId
	 *            彩金记录ID
	 * 
	 * @return 返回某条记录详情
	 */
	public String getCaiJinDonateDetail() {

		try {
			CaijinDonate result = null;
			if (isNotNull(this.caiJinLogId)) {
				result = payService
						.getCaiJinDonateDetailAdmin(this.caiJinLogId);
			}
			if (null == result) {
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

	/**
	 * 彩金系统后台 -- 添加一条彩金捐赠记录
	 * 
	 * @param caijinDonate
	 * @throws DaoException
	 */
	public String addOneCaiJinDonateLogAdmin() {
		try {
			if (isNotNull(this.getUserNames(), this.getApplyUserId(), this
					.getReason(),this.getCaijinMoney())) {
				String names=Util.formatInputNames(this.getUserNames());
				if (payService.checkInputUserNames(names)) {//检验用户名是否是系统内用户
					CaijinDonate caijinDonate = new CaijinDonate();
					Long donateNum = new Long(names.split(",").length);
					caijinDonate.setUserList(names);

					caijinDonate.setApplyUserId(this.getApplyUserId());
					caijinDonate.setApplyUser(this.getApplyUserName());
					caijinDonate.setDeptCode(this.getDeptCode());
					caijinDonate.setDeptName(this.getDeptName());

					BigDecimal money = this.getCaijinMoney();
					caijinDonate.setMoney(money);
					caijinDonate.setAllMoney(money.multiply(new BigDecimal(
							donateNum)));
					caijinDonate.setDonateNum(donateNum);

					caijinDonate.setReason(this.getReason());
					caijinDonate.setConcreteReason(this.getConcreteReason());

					CSHandleLog csHandleLog = generateCsHandleLog("添加一条彩金捐赠订单.");
					payService.addOneCaiJinDonateLogAdmin(caijinDonate,
							csHandleLog);
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(2, MSG_FAILURE, "用户名输入不合法");//提示检查用户名是否我们系统内用户 
				}
			}else{
				generateResult(0, MSG_FAILURE, "errors");	
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 彩金系统后台 -- 审核彩金订单
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public String auditCaiJinDonateAdmin() {
		try {
			if (isNotNull(this.getCaiJinLogId(), this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.auditCaiJinDonateAdmin(this.getCaiJinLogId(), this
						.getCsMemo(), csHandleLog);
				generateResult(1, MSG_SUCCESS, "");
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 彩金系统后台 -- 撤销彩金订单
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public String cancleCaiJinDonateAdmin() {
		try {

			if (isNotNull(this.getCaiJinLogId(), this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.cancleCaiJinDonateAdmin(this.getCaiJinLogId(), this
						.getCsMemo(), csHandleLog);
				generateResult(1, MSG_SUCCESS, "");
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 彩金系统后台 -- 客服填写备注
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public String saveCsHandleLog() {
		try {

			if (isNotNull(this.getCaiJinLogId(), this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				csHandleLog.setLogId(this.getCaiJinLogId());
				csHandleLog.setOpType("0");
				Long result = payService.saveCsHandleLogAdmin(csHandleLog);
				if (null != result) {
					generateResult(1, MSG_SUCCESS, "");
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 生成客服彩金模块操作记录
	 */
	private CSHandleLog generateCsHandleLog(String memo) {
		BackendUser admin = (BackendUser) session.get("admin");
		CSHandleLog csHandleLog = new CSHandleLog();
		csHandleLog.setCsUserId(admin.getUserid());
		csHandleLog.setCsName(admin.getName());
		csHandleLog.setType(Constants.ORDER_CATEGORY_MOSAIC_GOLD_ZS);
		csHandleLog.setIp(request.getRemoteAddr());
		csHandleLog.setMemo(memo);
		csHandleLog.setHandleTime(new Date());
		return csHandleLog;
	}

	/**
	 * 根据页面请求参数生成查询条件字符串
	 * 
	 * @return 返回查询条件字符串
	 */
	public StringBuilder generateQueryCondition() {
		StringBuilder queryCondition = new StringBuilder();		
		// 申请彩金时间区间 默认为无期限
		if (isNotNull(this.caiJinBeginTime) && !this.caiJinBeginTime.equals("")) {
			queryCondition.append(" and ( apply_time >= to_date('"
					+ this.caiJinBeginTime
					+ "','yyyy-MM-dd HH24:mi:ss') )");
		}
		if (isNotNull(this.caiJinEndTime) && !this.caiJinEndTime.equals("")) {
			queryCondition.append(" and ( apply_time <= to_date('"
					+ this.caiJinEndTime + "','yyyy-MM-dd HH24:mi:ss') )");
		}
		
		// 审核彩金时间区间 默认为无期限
		if (isNotNull(this.auditBeginTime) && !this.auditBeginTime.equals("")) {
			queryCondition.append(" and ( audit_time >= to_date('"
					+ this.auditBeginTime
					+ "','yyyy-MM-dd HH24:mi:ss') )");
		}
		if (isNotNull(this.auditEndTime) && !this.auditEndTime.equals("")) {
			queryCondition.append(" and ( audit_time <= to_date('"
					+ this.auditEndTime + "','yyyy-MM-dd HH24:mi:ss') )");
		}
		
		// 合计金额区间 值为空时 表示用户和没填
		if (isNotNull(this.caiJinMoneyMin, this.caiJinMoneyMax)) {
			if (!this.caiJinMoneyMin.equals("")) {
				queryCondition.append(" and ( all_money>="
						+ this.caiJinMoneyMax + ")");
			}
			if (!this.caiJinMoneyMax.equals("")) {
				queryCondition.append(" and ( all_money<="
						+ this.caiJinMoneyMax + ")");
			}
		}
		// 检索关键字 模糊查询
		if (isNotNull(this.queryFiled, this.queryFileValue)
				&& !this.queryFileValue.equals("")) {
			queryCondition.append(" and ( " + this.queryFiled + " like '%"
					+ this.queryFileValue + "%')");
		}
		// 订单状态 1：未审核 2.已审核 3.已撤销
		if (isNotNull(this.caiJinStatus) && !this.caiJinStatus.equals("0")) {
			queryCondition.append(" and ( status=" + this.caiJinStatus + ")");
		}
		// 申请人ID
		if (isNotNull(this.applyUserId) && this.applyUserId != 0L) {
			queryCondition.append(" and ( apply_user_id=" + this.applyUserId
					+ ")");
		}
		// 审核人Id
		if (isNotNull(this.auditUserId) && this.auditUserId != 0L) {
			queryCondition.append(" and ( audit_user_id=" + this.auditUserId
					+ ")");
		}
		return queryCondition;
	}

	public Long getCaiJinLogId() {
		return caiJinLogId;
	}

	public void setCaiJinLogId(Long caiJinLogId) {
		this.caiJinLogId = caiJinLogId;
	}

	public Long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}

	public Long getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getCaiJinBeginTime() {
		return caiJinBeginTime;
	}

	public void setCaiJinBeginTime(String caiJinBeginTime) {
		this.caiJinBeginTime = caiJinBeginTime;
	}

	public String getCaiJinEndTime() {
		return caiJinEndTime;
	}

	public void setCaiJinEndTime(String caiJinEndTime) {
		this.caiJinEndTime = caiJinEndTime;
	}

	public String getAuditBeginTime() {
		return auditBeginTime;
	}

	public void setAuditBeginTime(String auditBeginTime) {
		this.auditBeginTime = auditBeginTime;
	}

	public String getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public String getCaiJinStatus() {
		return caiJinStatus;
	}

	public void setCaiJinStatus(String caiJinStatus) {
		this.caiJinStatus = caiJinStatus;
	}

	public String getCaiJinMoneyMin() {
		return caiJinMoneyMin;
	}

	public void setCaiJinMoneyMin(String caiJinMoneyMin) {
		this.caiJinMoneyMin = caiJinMoneyMin;
	}

	public String getCaiJinMoneyMax() {
		return caiJinMoneyMax;
	}

	public void setCaiJinMoneyMax(String caiJinMoneyMax) {
		this.caiJinMoneyMax = caiJinMoneyMax;
	}

	public String getQueryFiled() {
		return queryFiled;
	}

	public void setQueryFiled(String queryFiled) {
		this.queryFiled = queryFiled;
	}

	public String getQueryFileValue() {
		return queryFileValue;
	}

	public void setQueryFileValue(String queryFileValue) {
		this.queryFileValue = queryFileValue;
	}

	public Integer getOrderTypeIndex() {
		return orderTypeIndex;
	}

	public void setOrderTypeIndex(Integer orderTypeIndex) {
		this.orderTypeIndex = orderTypeIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getIsCount() {
		return isCount;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
	}

	public String getIsCommit() {
		return isCommit;
	}

	public void setIsCommit(String isCommit) {
		this.isCommit = isCommit;
	}

	public PayService getPayService() {
		return payService;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getCsMemo() {
		return csMemo;
	}

	public void setCsMemo(String csMemo) {
		this.csMemo = csMemo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getConcreteReason() {
		return concreteReason;
	}

	public void setConcreteReason(String concreteReason) {
		this.concreteReason = concreteReason;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public BigDecimal getCaijinMoney() {
		return caijinMoney;
	}

	public void setCaijinMoney(BigDecimal caijinMoney) {
		this.caijinMoney = caijinMoney;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
