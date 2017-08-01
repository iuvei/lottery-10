package com.wintv.lottery.admin.pay.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.User;
import com.wintv.framework.pojo.WithdrawLog;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.pay.vo.WithdrawLogVo;
import com.wintv.lottery.user.service.UserService;

/**
 * 后台取款系统管理
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class WithdrawLogAdminAction extends BaseAction {
	private static final long serialVersionUID = -3706503032785489612L;
	private Long drawId;// 提现记录Id
	private Long userId;// 提现用户Id
	private String drawMoney;// 提现金额
	private String drawBeginTime = "2010-01-12";// 取款时间查询区间开始时间 格式:"yyyy-MM-dd"
	private String drawEndTime = "2010-01-18";// 取款时间查询区间结束时间 格式:"yyyy-MM-dd"
	private String drawStatus = "0";// 取款状态 未受理、已受理、已转账
	private String userGrade = "0";// 用户等级
	private String drawBank = "0";// 取款银行

	private String drawMoneyMin;// 投注金额查询区间 最小金额 格式:"123.00"
	private String drawMoneyMax;// 投注金额查询区间 最大金额 格式:"123.00"

	private String queryFiled = "userName";// 检索关键字段 用户名,订单号,取款IP
	private String queryFileValue = "";// 检索关键字段值

	private Integer orderTypeIndex = 0;// 排序类型索引 0,1,2,3,4,5
	private Integer pageSize = 20;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计

	private String logType = Constants.ORDER_CATEGORY_WITHDRAW;
	private String csMemo = "备注";// 客服填写的记录内容
	private User user;//取现用户
	private static final String[][] ORDER_TYPE_CONFIG = {
			{ "drawTime", "desc" },// 注册时间由近到远, 默认
			{ "drawTime", "asc" },// 注册时间由远到近
			{ "drawMoney", "asc" },// 取款金额由低到高
			{ "drawMoney", "desc" } };// 取款金额由高到低

	@Autowired
	private PayService payService;
	@Autowired
	private UserService userService;
	public String excute() {
		if (isNotNull(this.getUserId())) {
			user=userService.findUserByUserId(this.getUserId());
		}
		return SUCCESS;
	}

	/**
	 * 根据检索条件查询
	 * 
	 * @return 返回相应的查询结果
	 */
	public String searchWithDrawLogList() {
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
			List<WithdrawLogVo> drawLogs = payService.withdrawLogListAdmin(
					queryCondition, orderFiled, orderType, startRow, pageSize);
			Map<String, Object> total = new HashMap<String, Object>();
			if (this.isCount == 1) {
				total = payService.countWithDrawLogAdmin(queryCondition);
			}
			if (isNotNull(drawLogs, total)) {
				Map result = new HashMap();
				result.put("totalCount", total.get("totalCount") == null ? 0
						: total.get("totalCount"));
				result.put("totalMoney", total.get("totalMoney") == null ? 0
						: total.get("totalMoney"));
				result.put("drawLogs", drawLogs);
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
	 * 根据drawLogId查询提现明细
	 * 
	 * @return 返回相应的查询结果
	 */
	public String getWithDrawLogDetail() {
		try {
			WithdrawLog result = null;
			if (isNotNull(this.drawId)) {
				result = payService.loadWithDrawAdmin(drawId);
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
	 * 取款系统后台-取款成功操作
	 * 
	 * @author hikin yao
	 * @param drawLogId
	 *            //相应的取款记录id
	 * @param userId
	 *            //取款记录的用户Id
	 * @param csId
	 *            //执行操作的客服id
	 * @param csName
	 *            //执行操作的客服姓名
	 * @param csIP
	 *            //执行操作的客服ip
	 * @param drawMoney
	 *            //取款金额
	 * @throws DaoException
	 */
	// TODO:代码复查
	public String drawMoneySuccess() {
		try {
			if (isNotNull(this.drawId, this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.drawMoneySuccessAdmin(this.drawId, csHandleLog);
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
	 * 取款系统后台-撤销取款操作
	 * 
	 * @author hikin yao
	 * @param drawLogId
	 *            //相应的取款记录id
	 * @param userId
	 *            //取款记录的用户Id
	 * @param csId
	 *            //执行操作的客服id
	 * @param csName
	 *            //执行操作的客服姓名
	 * @param csIP
	 *            //执行操作的客服ip
	 * @param memo
	 *            //执行操作的客服填写的备注说明
	 * @param drawMoney
	 *            //取款金额
	 */

	public String cancelDrawMoney() {
		try {
			if (isNotNull(this.drawId, this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.cancelDrawMoneyAdmin(this.drawId, csHandleLog);
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
	 * 取款系统后台-受理取款操作
	 * 
	 * @author hikin yao
	 * @param drawLogId
	 *            //相应的取款记录id
	 * @param userId
	 *            //取款记录的用户Id
	 * @param csId
	 *            //执行操作的客服id
	 * @param csName
	 *            //执行操作的客服姓名
	 * @param csIP
	 *            //执行操作的客服ip
	 * 
	 */
	public String acceptDrawMoney() {
		try {
			if (isNotNull(this.drawId, this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.acceptDrawMoneyAdmin(this.getDrawId(), csHandleLog);
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
	 * 取款系统后台 —— 获取取款银行列表
	 * 
	 * @param chargeLog
	 * @throws DaoException
	 */
	public String findDrawBankList() {
		try {
			List<BankVo> result = payService.findWithdrawBank();
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
	 * 取款系统后台 -- 客服填写备注
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public String saveCsHandleLog() {
		try {			
			if (isNotNull(this.getDrawId(), this.getCsMemo())) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				csHandleLog.setLogId(this.getDrawId());
				csHandleLog.setOpType("0");
				Long result=payService.saveCsHandleLogAdmin(csHandleLog);
				if(null!=result){
					generateResult(1, MSG_SUCCESS, "");
				}else{
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
	 * 生成客服取款模块操作记录
	 */
	private CSHandleLog generateCsHandleLog(String memo) {
		BackendUser admin = (BackendUser) session.get("admin");
		CSHandleLog csHandleLog = new CSHandleLog();
		csHandleLog.setCsUserId(admin.getUserid());
		csHandleLog.setCsName(admin.getName());
		csHandleLog.setType(Constants.ORDER_CATEGORY_WITHDRAW);
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
		// 用户取款时间区间 默认为一周时间以内

		if (isNotNull(this.drawBeginTime) && !this.drawBeginTime.equals("")) {
			queryCondition.append(" and ( drawTime >= to_date('"
					+ this.drawBeginTime
					+ "','yyyy-MM-dd HH24:mi:ss') )");
		}
		if (isNotNull(this.drawEndTime) && !this.drawEndTime.equals("")) {
			queryCondition.append(" and ( drawTime <= to_date('"
					+ this.drawEndTime + "','yyyy-MM-dd HH24:mi:ss') )");
		}
		// 取款金额区间 值为空时 表示用户和没填
		if (isNotNull(this.drawMoneyMin, this.drawMoneyMax)) {
			if (!this.drawMoneyMin.equals("")) {
				queryCondition.append(" and ( drawMoney>=" + this.drawMoneyMin
						+ ")");
			}
			if (!this.drawMoneyMax.equals("")) {
				queryCondition.append(" and ( drawMoney<=" + this.drawMoneyMax
						+ ")");
			}
		}
		// 检索关键字 模糊查询
		if (isNotNull(this.queryFiled, this.queryFileValue)
				&& !this.queryFileValue.equals("")) {
			queryCondition.append(" and ( " + this.queryFiled + " like '%"
					+ this.queryFileValue + "%')");
		}
		// 用户等级
		if (isNotNull(this.userGrade) && !this.userGrade.equals("0")) {
			queryCondition.append(" and ( userGrade=" + this.userGrade + ")");
		}
		// 用户Id
		if (isNotNull(this.userId)) {
			queryCondition.append(" and ( userId=" + this.userId + ")");
		}
		// 取款银行
		if (isNotNull(this.drawBank) && !this.drawBank.equals("0")) {
			queryCondition.append(" and ( bankCode='" + this.drawBank + "')");
		}
		// 取款状态 1：正常 2.未受理 3.已受理 4.已转账
		if (isNotNull(this.drawStatus) && !this.drawStatus.equals("0")) {
			queryCondition.append(" and ( drawStatus=" + this.drawStatus + ")");
		}
		return queryCondition;
	}

	public String getDrawBeginTime() {
		return drawBeginTime;
	}

	public void setDrawBeginTime(String drawBeginTime) {
		this.drawBeginTime = drawBeginTime;
	}

	public String getDrawEndTime() {
		return drawEndTime;
	}

	public void setDrawEndTime(String drawEndTime) {
		this.drawEndTime = drawEndTime;
	}

	public String getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public String getDrawBank() {
		return drawBank;
	}

	public void setDrawBank(String drawBank) {
		this.drawBank = drawBank;
	}

	public String getDrawMoneyMin() {
		return drawMoneyMin;
	}

	public void setDrawMoneyMin(String drawMoneyMin) {
		this.drawMoneyMin = drawMoneyMin;
	}

	public String getDrawMoneyMax() {
		return drawMoneyMax;
	}

	public void setDrawMoneyMax(String drawMoneyMax) {
		this.drawMoneyMax = drawMoneyMax;
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

	public static String[][] getORDER_TYPE_CONFIG() {
		return ORDER_TYPE_CONFIG;
	}

	public Integer getOrderTypeIndex() {
		return orderTypeIndex;
	}

	public void setOrderTypeIndex(Integer orderTypeIndex) {
		this.orderTypeIndex = orderTypeIndex;
	}

	public Long getDrawId() {
		return drawId;
	}

	public void setDrawId(Long drawId) {
		this.drawId = drawId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDrawMoney() {
		return drawMoney;
	}

	public void setDrawMoney(String drawMoney) {
		this.drawMoney = drawMoney;
	}

	public Integer getIsCount() {
		return isCount;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
