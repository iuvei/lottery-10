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
import com.wintv.framework.pojo.ChargeLog;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.pay.vo.ChargeLogVo;
import com.wintv.lottery.user.service.UserService;

/**
 * 充值系统后台管理模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class ChargeLogAdminAction extends BaseAction {
	private static final long serialVersionUID = -3706503032785489612L;
	private Long chargeId;// 充值记录Id
	private Long userId;// 充值用户Id
	private String userName = "arix041212";// 充值用户姓名
	private String csMemo = "备注";// 客服填写的记录内容
	private String chargeBeginTime = "2010-01-12";// 充值时间查询区间开始时间
	// 格式:"yyyy-MM-dd"
	private String chargeEndTime = "2010-01-18";// 充值时间查询区间结束时间 格式:"yyyy-MM-dd"
	private String chargeStatus = "0";// 充值状态 未支付 已支付
	private String chargeWay = "0";// 充值方式
	private String userGrade = "0";// 用户等级
	private String chargeBank = "0";// 充值银行
	private String chargeBankName = "";// 充值银行名称

	private String chargeMoneyMin;// 充值金额查询区间 最小金额 格式:"123.00"
	private String chargeMoneyMax;// 充值金额查询区间 最大金额 格式:"123.00"
	private BigDecimal chargeMoney = new BigDecimal(0);// 充值金额

	private String queryFiled = "userName";// 检索关键字段 用户名、订单号、取款IP
	private String queryFileValue = "";// 检索关键字段值

	private Integer orderTypeIndex = 0;// 排序类型索引 0,1,2,3,4,5
	private Integer pageSize = 20;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private String isCommit = "no";// 是否提交 客服填写备注说明
	private User user;//充值用户
	private String logType=Constants.ORDER_CATEGORY_CHARGE;
	
	private static final String[][] ORDER_TYPE_CONFIG = {
			{ "chargeTime", "desc" },// 充值时间由近到远
			{ "chargeTime", "asc" },// 充值时间由远到近
			{ "chargeMoney", "asc" },// 充值金额由低到高
			{ "chargeMoney", "desc" } };// 充值金额由高到低

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
	public String searchChargeLogList() {
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
			List<ChargeLogVo> chargeLogs = payService.findChargeLogListAdmin(
					queryCondition, orderFiled, orderType, startRow, pageSize);
			Map<String, Object> total = new HashMap<String, Object>();
			if (this.isCount == 1) {
				total = payService.countChargeLogAdmin(queryCondition);
			}
			if (isNotNull(chargeLogs, total)) {
				Map result = new HashMap();
				result.put("totalCount", total.get("totalCount") == null ? 0
						: total.get("totalCount"));
				result.put("totalMoney", total.get("totalMoney") == null ? 0
						: total.get("totalMoney"));
				result.put("chargeLogs", chargeLogs);
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
	 * 根据chargeLogId查询充值明细
	 * 
	 * @return 返回相应的查询结果
	 */
	public String getChargeLogDetail() {
		try {
			ChargeLog result = null;
			if (isNotNull(this.chargeId)) {
				result = payService.getChargeLogDetailAdmin(chargeId);
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
	 * 充值系统后台——到款确认 确认提交则将订单状态改为已支付,记录客服操作
	 * 
	 * @param chargeLog
	 *            充值记录
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * @param isCommit
	 *            客服是否确认提交,不提交则只执行操作日志记录 ok.确认提交 no.不提交 都要填写充值纪录
	 * 
	 */
	public String confirmBankTransfer() {
		try {
			
			if (isNotNull(this.chargeId,this.csMemo,this.isCommit)) {
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.confirmBankTransferAdmin(this.chargeId,
						csHandleLog, this.isCommit);
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 充值系统后台 —— 添加一条充值记录
	 * 
	 * @param chargeLog
	 * @throws DaoException
	 */
	public String addOneChargeLog() {
		try {
			String userName = this.getUserName();
			if (null != userName && !userName.equals("")) {
				User chargeUser = userService.findUserByUserName(userName
						.trim());
				if (null == chargeUser) {
					generateResult(0, MSG_FAILURE, "您输入的充值用户名不正确!");
					return SUCCESS;
				}
				ChargeLog chargeLog = generateCSAddChargeLog(chargeUser
						.getUserid());
				chargeLog.setMemo(this.getCsMemo());
				CSHandleLog csHandleLog = generateCsHandleLog(this.getCsMemo());
				payService.addOneChangeLogAdmin(chargeLog, csHandleLog);
				generateResult(1, MSG_SUCCESS, csHandleLog);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "系统忙，请稍后再试..");
		}
		return SUCCESS;
	}
    /**
     * 添加充值订单时实时判断充值用户名是否是本站用户
     */
	public String isUserNameExited() {
		try {
			String userName = this.getUserName();
			if (null != userName && !userName.equals("")) {
				boolean result=userService.isExistUser(userName);
				if(result){
					generateResult(1, MSG_SUCCESS, result);
				}else{
					generateResult(0, MSG_FAILURE, result);
				}
			}else{
				generateResult(0, MSG_FAILURE, "请输入用户名");
			}			
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "发生异常");
		}
		return SUCCESS;
	}

	/**
	 * 充值系统后台 —— 获取充值银行列表 
	 * 
	 * @param chargeLog
	 * @throws DaoException
	 */
	public String findChargeBankList() {
		try {
			List<BankVo> result = payService.findChargeBank();
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
	 * 根据添加充值订单页面参数生成充值对象
	 * 
	 * @return
	 */
	private ChargeLog generateCSAddChargeLog(Long chargeUserId) {
		ChargeLog chargeLog = new ChargeLog();
		chargeLog.setPayWay(this.getChargeWay());
		chargeLog.setFromBank(this.getChargeBankName());
		chargeLog.setFromBankCode(this.getChargeBank());
		chargeLog.setUserId(chargeUserId);
		chargeLog.setMoney(this.getChargeMoney());
		chargeLog.setIp(request.getRemoteAddr());
		return chargeLog;
	}

	/**
	 * 生成客服充值模块操作记录
	 */
	private CSHandleLog generateCsHandleLog(String memo) {
		BackendUser admin = (BackendUser) session.get("admin");
		CSHandleLog csHandleLog = new CSHandleLog();
		csHandleLog.setCsUserId(admin.getUserid());
		csHandleLog.setCsName(admin.getName());
		csHandleLog.setType(Constants.ORDER_CATEGORY_CHARGE);
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
	private StringBuilder generateQueryCondition() {
		StringBuilder queryCondition = new StringBuilder();
		// 充值取款时间区间 默认为一周时间以内
		if (isNotNull(this.chargeBeginTime) && !this.chargeBeginTime.equals("")) {
			queryCondition.append(" and ( chargeTime >= to_date('"
					+ this.chargeBeginTime
					+ "','yyyy-MM-dd HH24:mi:ss') )");
		}
		if (isNotNull(this.chargeEndTime) && !this.chargeEndTime.equals("")) {
			queryCondition.append(" and ( chargeTime <= to_date('"
					+ this.chargeEndTime + "','yyyy-MM-dd HH24:mi:ss') )");
		}
		// 充值金额区间 值为空时 表示用户和没填
		if (isNotNull(this.chargeMoneyMin, this.chargeMoneyMax)) {
			if (!this.chargeMoneyMin.equals("")) {
				queryCondition.append(" and ( chargeMoney>="
						+ this.chargeMoneyMax + ")");
			}
			if (!this.chargeMoneyMax.equals("")) {
				queryCondition.append(" and ( chargeMoney<="
						+ this.chargeMoneyMax + ")");
			}
		}
		// 检索关键字 模糊查询
		if (isNotNull(this.queryFiled, this.queryFileValue)
				&& !this.queryFileValue.equals("")) {
			queryCondition.append(" and ( " + this.queryFiled + " like '%"
					+ this.queryFileValue + "%')");
		}
		// 充值方式
		if (isNotNull(this.chargeWay) && !this.chargeWay.equals("0")) {
			queryCondition.append(" and ( chargeWay=" + this.chargeWay + ")");
		}
		// 用户等级
		if (isNotNull(this.userGrade) && !this.userGrade.equals("0")) {
			queryCondition.append(" and ( userGrade=" + this.userGrade + ")");
		}
		// 用户Id
		if (isNotNull(this.userId)) {
			queryCondition.append(" and ( userId=" + this.userId + ")");
		}
		// 充值银行
		if (isNotNull(this.chargeBank) && !this.chargeBank.equals("0")) {
			queryCondition.append(" and ( chargeBankCode='" + this.chargeBank
					+ "')");
		}
		// 充值状态 1：未支付 2.已支付
		if (isNotNull(this.chargeStatus) && !this.chargeStatus.equals("0")) {
			queryCondition.append(" and ( chargeStatus=" + this.chargeStatus
					+ ")");
		}
		return queryCondition;
	}

	

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getChargeBeginTime() {
		return chargeBeginTime;
	}

	public void setChargeBeginTime(String chargeBeginTime) {
		this.chargeBeginTime = chargeBeginTime;
	}

	public String getChargeEndTime() {
		return chargeEndTime;
	}

	public void setChargeEndTime(String chargeEndTime) {
		this.chargeEndTime = chargeEndTime;
	}

	public String getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getChargeWay() {
		return chargeWay;
	}

	public void setChargeWay(String chargeWay) {
		this.chargeWay = chargeWay;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public String getChargeBank() {
		return chargeBank;
	}

	public void setChargeBank(String chargeBank) {
		this.chargeBank = chargeBank;
	}

	public String getChargeMoneyMin() {
		return chargeMoneyMin;
	}

	public void setChargeMoneyMin(String chargeMoneyMin) {
		this.chargeMoneyMin = chargeMoneyMin;
	}

	public String getChargeMoneyMax() {
		return chargeMoneyMax;
	}

	public void setChargeMoneyMax(String chargeMoneyMax) {
		this.chargeMoneyMax = chargeMoneyMax;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(BigDecimal chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public String getCsMemo() {
		return csMemo;
	}

	public void setCsMemo(String csMemo) {
		this.csMemo = csMemo;
	}

	public String getChargeBankName() {
		return chargeBankName;
	}

	public void setChargeBankName(String chargeBankName) {
		this.chargeBankName = chargeBankName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
