package com.wintv.lottery.xinshui.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderDetailVO;

/**
 * 心水支付模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class XinshuiPayAction extends BaseAction {
	private static final long serialVersionUID = -7794218343958143509L;
	@Autowired
	private XinshuiService xinshuiService;
	@Autowired
	private PayService payService;

	private BankVo bank;
	private List<BankVo> bankVoList;
	private UserCookie currentUser;

	private Long xinshuiId;// 心水ID
	private Long xinshuiOrderId;//心水订单ID
	private String isUseCaijin;// 是否使用彩金 1.使用 2.不使用
	
	private XinshuiDetailVO xinshuiDetail;
	private XinshuiOrderDetailVO xinshuiOrderDetail;

	public String excute() {
		try {
			currentUser = (UserCookie) session.get("userCookie");
			if (null == currentUser) {
				String url = "/xinshui/pay.html?xinshuiId="
						+ this.getXinshuiId();
				session.put("loginRedirectUrl", url);
				return "login";
			} else {
				session.remove("loginRedirectUrl");
			}

			// 获取上一次取款银行信息
			bank = payService.findLastChargeBank(currentUser.getUserId());
			bankVoList = payService.findChargeBank();
			// 银行列表中去除推荐银行，避免重复
			Iterator it = bankVoList.iterator();
			while (it.hasNext()) {
				BankVo bankTemp = (BankVo) it.next();
				if (bankTemp.getBankCode().equals(bank.getBankCode())) {
					it.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	public String pay2() {
		try {
			Long userId = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
			xinshuiOrderDetail = xinshuiService.loadXinshuiOrderDetail(this.getXinshuiOrderId());
			// 获取上一次取款银行信息
			bank = payService.findLastChargeBank(userId);
			bankVoList = payService.findChargeBank();
			// 银行列表中去除推荐银行，避免重复
			Iterator it = bankVoList.iterator();
			while (it.hasNext()) {
				BankVo bankTemp = (BankVo) it.next();
				if (bankTemp.getBankCode().equals(bank.getBankCode())) {
					it.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 根据心水Id获取相应心水基本信息
	 */
	public String getXinshuiDetail() {
		try {
			if (isNotNull(this.getXinshuiId())) {
				Long xinshuiId = this.getXinshuiId();
				XinshuiDetailVO xinshuiDetail = xinshuiService
						.loadXinshuiDetailVO(xinshuiId);
				generateResult(1, MSG_SUCCESS, xinshuiDetail);
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
	 * 获取当前用户账户信息
	 */
	public String getCurrentUserAccountInfo() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");

			if (isNotNull(currentUser)) {
				VirtualAccount userAccount = payService
						.findVirtualAccount(currentUser.getUserId());
				generateResult(1, MSG_SUCCESS, userAccount);
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
	 * 判断当前c2c产品用户是否已经购买
	 */
	public String isAlreadyBuyTheProduct() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser, this.getXinshuiId())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("c2cId", this.getXinshuiId());
				params.put("buyUserId", currentUser.getUserId());
				Integer result = xinshuiService.isAllowBuy(params) == true ? 1
						: 2;//1.没有购买，允许购买 2.已经购买，不允许再购买
				generateResult(1, MSG_SUCCESS, result);
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
	 * 执行确认支付操作
	 * 
	 * @return
	 */
	public String confirmPay() {
		try {
			currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(xinshuiId, currentUser, isUseCaijin)) {
				String result = payService.myXinshuiPay(xinshuiId, currentUser
						.getUserId(), isUseCaijin,currentUser.getUsername());
				generateResult(1, MSG_SUCCESS, result);
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
	 * 执行从银行支付操作
	 * 
	 * @return
	 */
	public String confirmPayToBank() {
		try {
			if (isNotNull(xinshuiId, currentUser, isUseCaijin)) {
				String result = payService.myXinshuiPay(xinshuiId, currentUser
						.getUserId(), isUseCaijin,currentUser.getUsername());
				generateResult(1, MSG_SUCCESS, result);
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

	public Long getXinshuiId() {
		return xinshuiId;
	}

	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}

	public String getIsUseCaijin() {
		return isUseCaijin;
	}

	public void setIsUseCaijin(String isUseCaijin) {
		this.isUseCaijin = isUseCaijin;
	}

	public BankVo getBank() {
		return bank;
	}

	public void setBank(BankVo bank) {
		this.bank = bank;
	}

	public List<BankVo> getBankVoList() {
		return bankVoList;
	}

	public void setBankVoList(List<BankVo> bankVoList) {
		this.bankVoList = bankVoList;
	}

	public UserCookie getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserCookie currentUser) {
		this.currentUser = currentUser;
	}

	public Long getXinshuiOrderId() {
		return xinshuiOrderId;
	}

	public void setXinshuiOrderId(Long xinshuiOrderId) {
		this.xinshuiOrderId = xinshuiOrderId;
	}

	public XinshuiOrderDetailVO getXinshuiOrderDetail() {
		return xinshuiOrderDetail;
	}

	public void setXinshuiOrderDetail(XinshuiOrderDetailVO xinshuiOrderDetail) {
		this.xinshuiOrderDetail = xinshuiOrderDetail;
	}
}
