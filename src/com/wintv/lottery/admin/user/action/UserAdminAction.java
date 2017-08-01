package com.wintv.lottery.admin.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.admin.user.service.UserAdminService;
import com.wintv.lottery.admin.user.vo.UserAccountInfoVO;
import com.wintv.lottery.admin.user.vo.UserAdminVO;
import com.wintv.lottery.admin.user.vo.UserInfoVO;
import com.wintv.lottery.admin.user.vo.UserXinshuiVO;
import com.wintv.framework.common.OnlineUserIdInSession;

/**
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class UserAdminAction extends BaseAction {
	private static final long serialVersionUID = 3172036547183315441L;
	private Long userId;// 用户Id
	private String checkedIds;//选中的用户id
	private String regBeginTime;// 注册时间查询区间开始时间 格式:"yyyy-MM-dd"
	private String regEndTime;// 注册时间查询区间结束时间 格式:"yyyy-MM-dd"

	private String accountBalanceMin;// 账户余额查询区间 最小余额 格式:"123.00"
	private String accountBalanceMax;// 账户余额查询区间 最大余额 格式："123.00"

	private String betMoneyMin;// 投注金额查询区间 最小金额 格式:"123.00"
	private String betMoneyMax;// 投注金额查询区间 最大金额 格式:"123.00"

	private String provinceCode;// 会员所属省份code
	private String cityCode;// 会员所属城市code

	private String queryFiled;// 检索关键字段username,name,email,qq,reg_ip,mp,idcard
	private String queryFileValue;// 检索关键字段值

	private String userGrade="0";// 用户等级
	private String userHonor="0";// 用户头衔
	private Integer userLockStatus=0;// 用户锁定状态 1：正常 2.登陆锁定 3.资金锁定

	private Integer orderTypeIndex=0;// 排序类型索引 0,1,2,3,4,5
	private Integer pageSize=20;// 页面显示数据条数
	private Integer startRow=1;// 查询开始行

	private Integer isCount=0;// 是否统计标志 1.统计 0.不统计

	private static final String[][] ORDER_TYPE_CONFIG = {
			{ "regTime", "asc" },// 注册时间由近到远, 默认
			{ "regTime", "desc" },// 注册时间由远到近
			{ "accountBalance", "asc" },// 账户金额由低到高
			{ "accountBalance", "desc" },// 账户金额由高到低
			{ "totalBet", "asc" },// 投注金额由低到高
			{ "totalBet", "desc" } };// 投注金额由高到低

	@Autowired
	private UserAdminService userAdminService;

	public String excute() {
		return SUCCESS;
	}
    
	/**
	 * 更改用户状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param status
	 *            用户锁定状态 1：正常 2.登陆锁定 3.资金锁定
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public String updateUserStatus() {
		try {
			Integer result = null;
			if (isNotNull(this.checkedIds, this.userLockStatus)) {
				result = userAdminService.updateUserStatus(this.checkedIds, this.userLockStatus);
			}
			if (null == result) {
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 更改用户登录状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param status
	 *            1.正常 2.登录锁定
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public String updateUserLoginStatus() {
		try {
			Integer result = null;
			if (isNotNull(this.checkedIds, this.userLockStatus)) {
				result = userAdminService.updateUserLoginStatus(this.checkedIds,
						this.userLockStatus.toString());
			}
			if (null == result) {
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 更改用户资金状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param status
	 *            1.正常 2.资金锁定
	 * @throws DaoException：数据操作异常
	 */
	public String updateUserAccountStatus() {
		try {
			Integer result = null;
			if (isNotNull(this.checkedIds, this.userLockStatus)) {
				result = userAdminService.updateUserAccountStatus(this.checkedIds,
						this.userLockStatus.toString());
			}
			if (null == result) {
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 根据检索条件查询
	 * 
	 * @return 返回相应的查询结果
	 */
	public String searchUserList() {
		String orderFiled = "";
		String orderType = "";
		if (isNotNull(this.orderTypeIndex)
				&& this.orderTypeIndex < ORDER_TYPE_CONFIG.length) {
			orderFiled = ORDER_TYPE_CONFIG[orderTypeIndex][0];
			orderType = ORDER_TYPE_CONFIG[orderTypeIndex][1];
		}
		// 根据页面参数生成查询条件
		StringBuilder queryCondition = generateQueryCondition();
		List<UserAdminVO> users = userAdminService.searchUserList(
				queryCondition, orderFiled, orderType, startRow, pageSize);
		Integer totalCount = 0;
		if (this.isCount == 1) {
			totalCount = userAdminService.countUsersBySearch(queryCondition);
		}

		if (isNotNull(users, totalCount)) {
			Map result = new HashMap();
			result.put("totalCount", totalCount);
			result.put("users", users);
			generateResult(1, MSG_SUCCESS, result);
		} else {
			generateResult(0, MSG_FAILURE, "errors");
		}
		return SUCCESS;
	}

	/**
	 * 根据检索条件统计总纪录数
	 * 
	 * @return 返回符合条件的总纪录条数
	 */
	public String countUserBySearch() {
		// 根据页面参数生成查询条件
		StringBuilder queryCondition = generateQueryCondition();
		Integer result = userAdminService.countUsersBySearch(queryCondition);
		if (null == result) {
			generateResult(0, MSG_FAILURE, "errors");
		} else {
			generateResult(1, MSG_SUCCESS, result);
		}
		return SUCCESS;
	}

	/**
	 * 获取用户基本信息与附加信息
	 * 
	 * @param userId
	 * @return 返回用户对象
	 */
	public String getUserInfo() {
		try {
			UserInfoVO result = null;
			if (isNotNull(this.userId)) {
				result = userAdminService.getUserInfo(this.userId);
			}
			if (null == result) {
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 获取会员账户信息
	 * 
	 * @param userId
	 *            所查询用户ID
	 * 
	 * @return 返回用户对象
	 */
	public String getUserAccountInfo() {
		UserAccountInfoVO result = null;
		if (isNotNull(this.userId)) {
			result = userAdminService.getUserAccountInfo(this.userId);
		}
		if (null == result) {
			generateResult(0, MSG_FAILURE, "errors");
		} else {
			generateResult(1, MSG_SUCCESS, result);
		}
		return SUCCESS;
	}
	/**
	 * 获取会员心水信息
	 * 
	 * @param userId
	 *            所查询用户ID
	 * 
	 * @return 返回用户对象
	 */
	public String getUserXinshuiInfo() {
		UserXinshuiVO result = new UserXinshuiVO();
		if (isNotNull(this.userId)) {
			result = userAdminService.loadUserXinshuiInfo(this.userId);
		}
		if (null == result) {
			generateResult(0, MSG_FAILURE, "errors");
		} else {
			generateResult(1, MSG_SUCCESS, result);
		}
		return SUCCESS;
	}

	/**
	 * 判断用户是否在线
	 * 
	 * @return 在线返回true 否则返回false
	 */
	public String isUserOnline() {
		Boolean result = null;
		if (isNotNull(this.userId)) {
			result = OnlineUserIdInSession.isUserOnline(this.userId);
		}
		if (null == result) {
			generateResult(0, MSG_FAILURE, "errors");
		} else {
			generateResult(1, MSG_SUCCESS, result);
		}
		return SUCCESS;
	}

	/**
	 * 根据页面请求参数生成查询条件字符串
	 * 
	 * @return 返回查询条件字符串
	 */
	public StringBuilder generateQueryCondition() {
		StringBuilder queryCondition = new StringBuilder();
		// 用户注册时间区间 默认为当天时间
		if (isNotNull(this.regBeginTime) && !this.regBeginTime.equals("")) {
			queryCondition.append(" and ( regTime >= to_date('"
					+ this.regBeginTime
					+ "','yyyy-MM-dd HH24:mi:ss') )");
		}
		if (isNotNull(this.regEndTime) && !this.regEndTime.equals("")) {
			queryCondition.append(" and ( regTime <= to_date('"
					+ this.regEndTime + "','yyyy-MM-dd HH24:mi:ss') )");
		}
		// 账号余额区间 值为-1时 表示用户和没填
		if (isNotNull(this.accountBalanceMin, this.accountBalanceMax)) {
			if(!this.accountBalanceMin.equals("")){
				queryCondition.append(" and ( accountBalance>=" + this.accountBalanceMin
						+ ")");
			}
			if(!this.accountBalanceMax.equals("")){
				queryCondition.append(" and ( accountBalance<=" + this.accountBalanceMax
						+ ")");
			}
		}
		// 总投注金额区间
		if (isNotNull(this.betMoneyMin, this.betMoneyMax)&&!this.betMoneyMin.equals("")&&!this.betMoneyMax.equals("")) {
			if(!this.betMoneyMin.equals("")){
				queryCondition.append(" and ( totalBet>=" + this.betMoneyMin
						+ ")");
			}
			if(!this.betMoneyMax.equals("")){
				queryCondition.append(" and ( totalBet<=" + this.betMoneyMax
						+ ")");
			}
		}
		// 会员地区
		if (isNotNull(this.provinceCode, this.cityCode)) {
			if(!this.provinceCode.equals("000")){
				queryCondition.append(" and ( provinceCode='" + this.provinceCode
						+ "' )");
			}
			if(!this.cityCode.equals("000")){
				queryCondition.append(" and ( cityCode='" + this.cityCode
						+ "' )");
			}
		}
		// 检索关键字 模糊查询
		if (isNotNull(this.queryFiled, this.queryFileValue)&&!this.queryFileValue.equals("")) {
			queryCondition.append(" and ( " + this.queryFiled + " like '%"
					+ this.queryFileValue + "%')");
		}
		// 用户等级
		if (isNotNull(this.userGrade)&&!this.userGrade.equals("0")) {
				queryCondition.append(" and ( userGrade=" + this.userGrade
						+ ")");
		}
		// 用户头衔
		if (isNotNull(this.userHonor)&&!this.userHonor.equals("0")) {
				queryCondition.append(" and ( userHonor=" + this.userHonor
						+ ")");
		}
		// 用户锁定状态 1：正常 2.登陆锁定 3.资金锁定
		if (isNotNull(this.userLockStatus)) {
			switch (this.userLockStatus) {
			case 1:// /正常
				queryCondition
						.append(" and (userStatus=1 and userMoneyStatus=1)");
				break;
			case 2:// 登陆锁定
				queryCondition.append(" and (userStatus=2 and userMoneyStatus=2)");
				break;
			case 3:// 资金锁定
				queryCondition.append(" and (userStatus=1 and userMoneyStatus=2)");
				break;
			default:
				break;
			}
		}
		return queryCondition;
	}

	public String getRegBeginTime() {
		return regBeginTime;
	}

	public void setRegBeginTime(String regBeginTime) {
		this.regBeginTime = regBeginTime;
	}

	public String getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(String regEndTime) {
		this.regEndTime = regEndTime;
	}

	public String getAccountBalanceMin() {
		return accountBalanceMin;
	}

	public void setAccountBalanceMin(String accountBalanceMin) {
		this.accountBalanceMin = accountBalanceMin;
	}

	public String getAccountBalanceMax() {
		return accountBalanceMax;
	}

	public void setAccountBalanceMax(String accountBalanceMax) {
		this.accountBalanceMax = accountBalanceMax;
	}

	public String getBetMoneyMin() {
		return betMoneyMin;
	}

	public void setBetMoneyMin(String betMoneyMin) {
		this.betMoneyMin = betMoneyMin;
	}

	public String getBetMoneyMax() {
		return betMoneyMax;
	}

	public void setBetMoneyMax(String betMoneyMax) {
		this.betMoneyMax = betMoneyMax;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public String getUserHonor() {
		return userHonor;
	}

	public void setUserHonor(String userHonor) {
		this.userHonor = userHonor;
	}

	public Integer getUserLockStatus() {
		return userLockStatus;
	}

	public void setUserLockStatus(Integer userLockStatus) {
		this.userLockStatus = userLockStatus;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIsCount() {
		return isCount;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}
	
}
