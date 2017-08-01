package com.wintv.lottery.pay.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.framework.pojo.WithdrawLog;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.pay.service.PayService;

@SuppressWarnings("unchecked")
public class WithdrawAction extends BaseAction {
	private static final long serialVersionUID = -2850989318289032938L;

	@Autowired
	private PayService payService;
	
	private BigDecimal money;//取款金额 
	private String withdrawPwd;//取款密码
	private String checkcode;//验证码
	private BigDecimal fee;//银行手续费
	private Integer pageSize=20;// 页面显示数据条数
	private Integer startRow=1;// 查询开始行
	private Long isCount=0L;// 是否统计标志 1.统计 0.不统计
	private String withdrawBeginTime;// 取款时间查询区间开始时间 格式:"yyyy-MM-dd"
	private String withdrawEndTime;// 取款时间查询区间结束时间 格式:"yyyy-MM-dd"
	private String withdrawStatus="0";// 取款状态 1：未受理 2.已受理 3.已转账 4.用户撤销 5.客服撤销
	private String orderNo;//取款订单号
	
	/**
	 * 跳转到我要取款页面
	 * @return
	 */
	public String withdraw() {
		try {
			Long userid = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
			VirtualAccount virtualAccount = payService.findVirtualAccount(userid);
			request.getSession().setAttribute("virtualAccount", virtualAccount);
			
			if("2".equals(virtualAccount.getStatus())) {
				request.setAttribute("forbidWithdraw", 1);//资金账户被锁定，禁止取款
				return SUCCESS;
			}
			
			if(!isNotNull(virtualAccount.getBankCode(), virtualAccount.getBankName(), virtualAccount.getCardNum())) {
				request.setAttribute("forbidWithdraw", 2);//银行信息没有绑定，禁止取款
				return SUCCESS;
			}
			
			if(payService.findWithdrawTimes(userid) >= 3) {
				request.setAttribute("forbidWithdraw", 3);//当天取款次数超过3次，禁止取款
				return SUCCESS;
			}
			
			if(payService.findErrorPwdTimes(userid) >= 10) {
				request.setAttribute("forbidWithdraw", 4);//当天取款密码错误次数超过10次，禁止取款
				return SUCCESS;
			}
			
			if(request.getSession().getAttribute("errorWithdrawPwdTimes") != null) {
				int errorWithdrawPwdTimes = (Integer)request.getSession().getAttribute("errorWithdrawPwdTimes");
				if (errorWithdrawPwdTimes >= 3) {
					request.setAttribute("checkcodeDiv", 1);
				}
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 我要取款下一步
	 * @return
	 */
	public String withdrawStep2() {
		try {
			if(checkcode != null) {
				if(!request.getSession().getAttribute("withdrawCheckcode").toString().toLowerCase().equals(checkcode.toLowerCase())) {
					request.setAttribute("errorCode", 1);//验证码错误
					request.setAttribute("checkcodeDiv", 1);
					return "fail";
				}
			}
			
			if(money == null || money.compareTo(new BigDecimal(0)) == 0) {
				request.setAttribute("errorCode", 2);//取款金额不能为空
				return "fail";
			}
			
			VirtualAccount virtualAccount = (VirtualAccount)request.getSession().getAttribute("virtualAccount");
			
			if(money.compareTo(new BigDecimal(10)) < 0 && money.compareTo(virtualAccount.getAllMoney().subtract(virtualAccount.getFrozenMoney())) != 0) {
				request.setAttribute("errorCode", 3);//取款金额不能低于10元(如果清户取款则没有限制)
				return "fail";
			}
			
			if(money.compareTo(virtualAccount.getAllMoney().subtract(virtualAccount.getFrozenMoney())) > 0) {
				request.setAttribute("errorCode", 4);//可用取款金额不足
				return "fail";
			}
			
			//取款密码错误计数器
			int errorWithdrawPwdTimes = 0;
			if(request.getSession().getAttribute("errorWithdrawPwdTimes") != null) {
				errorWithdrawPwdTimes =(Integer)request.getSession().getAttribute("errorWithdrawPwdTimes");
			}
			
			Long userid = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
			
			//取款密码验证
			if(!payService.authWithdrawPassword(userid, withdrawPwd)) {
				payService.setErrorPwdTimes(userid);
				if (errorWithdrawPwdTimes >= 2) {
					request.setAttribute("checkcodeDiv", 1);
				}
				if(payService.findErrorPwdTimes(userid) >= 10) {
					request.setAttribute("forbidWithdraw", 4);
				}
				request.getSession().setAttribute("errorWithdrawPwdTimes", errorWithdrawPwdTimes + 1);
				request.setAttribute("errorCode", 5);//取款密码错误
				return "fail";
			} else {
				request.getSession().removeAttribute("errorWithdrawPwdTimes");
				request.getSession().removeAttribute("withdrawCheckcode");
			}
			
			fee = new BigDecimal(2);//**********默认手续费2元，后期需要更改**********
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		} 
		return SUCCESS;
	}
	
	/**
	 * 确认取款
	 * @return
	 */
	public String withdrawConfirm() {
		try {
			VirtualAccount virtualAccount = (VirtualAccount)request.getSession().getAttribute("virtualAccount");

			System.out.println();
			
			Map params = new HashMap();
			params.put("userId", virtualAccount.getTxUserId());
			params.put("money", money);
			params.put("fee", fee);
			params.put("bank", virtualAccount.getBankName());
			params.put("bankCode", virtualAccount.getBankCode());
			params.put("bankProvince", virtualAccount.getProvince());
			params.put("bankCity", virtualAccount.getCity());
			params.put("branch", virtualAccount.getBranch());
			params.put("cardNum", virtualAccount.getCardNum());
			params.put("withdrawIp", request.getRemoteAddr());
			
			String withdrawStatus = payService.withdraw(params);
			
			//返回值1为取款成功
			if(!"1".equals(withdrawStatus)) {
				request.setAttribute("errorCode", withdrawStatus);
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 查询取款记录
	 * @return
	 */
	public String searchWithdrawList() {
		try {
			Map params = this.getParams();
			
			//总记录数
			Long totalCount = 0L;
			if (this.isCount == 1) {
				totalCount = payService.findWithdrawLogListCount(params);
			}
			
			//查询取款明细
			List<WithdrawLog> withdrawLogList = payService.findWithdrawLogList(params, startRow, pageSize);
			
			if (isNotNull(withdrawLogList, totalCount)) {
				Map result = new HashMap();
				result.put("totalCount", totalCount);
				result.put("withdrawLogList", withdrawLogList);
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 撤销取款
	 * @return
	 */
	public String userCancelWithdraw() {
		try {
			Map params = new HashMap();
			String orderNo = (String)request.getParameter("orderNo");
			params.put("orderNo", orderNo);
			if(payService.userCancelWithdraw(params)) {
				generateResult(1, MSG_SUCCESS, "success");
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 获取页面参数
	 * @return
	 */
	public Map getParams() {
		Map params = new HashMap();
		Long userid = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
		params.put("userid", userid);
		
		if(!StringUtils.isEmpty(withdrawBeginTime)) {
			params.put("startDay", DateUtil.parseDate(this.withdrawBeginTime));
		}
		if(!StringUtils.isEmpty(withdrawEndTime)) {
			params.put("endDay",  DateUtil.parseDate(this.withdrawEndTime));		
		}
		if(isNotNull(withdrawStatus)) {
			if(!"0".equals(withdrawStatus)) {
				params.put("status", withdrawStatus);
			}
		}
		return params;
	}
	
	/**----------------------------------------get&set----------------------------------------**/
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getWithdrawPwd() {
		return withdrawPwd;
	}

	public void setWithdrawPwd(String withdrawPwd) {
		this.withdrawPwd = withdrawPwd;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public Long getIsCount() {
		return isCount;
	}

	public void setIsCount(Long isCount) {
		this.isCount = isCount;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public String getWithdrawBeginTime() {
		return withdrawBeginTime;
	}

	public void setWithdrawBeginTime(String withdrawBeginTime) {
		this.withdrawBeginTime = withdrawBeginTime;
	}

	public String getWithdrawEndTime() {
		return withdrawEndTime;
	}

	public void setWithdrawEndTime(String withdrawEndTime) {
		this.withdrawEndTime = withdrawEndTime;
	}

	public String getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
