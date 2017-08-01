package com.wintv.lottery.b2c.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.lottery.b2c.service.B2CService;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderDetailVO;

/**
 * 心水B2c支付模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class B2cPayAction extends BaseAction {
	private static final long serialVersionUID = -7794218343958143509L;
	@Autowired
	private B2CService b2CService;
	@Autowired
	private PayService payService;

	private BankVo bank;
	private List<BankVo> bankVoList;
	private UserCookie currentUser;
	
	private Long b2cId;//b2c产品Id
	private Long orderId;//b2c订单Id
	private Long expertId;//专家Id
	private String isUseCaijin;// 是否使用彩金 1.使用 2.不使用
	private String orderType;//订购类型 1=包周 2=包月 3=包季 4=包年
	
	private XinshuiDetailVO xinshuiDetail;
	private XinshuiOrderDetailVO xinshuiOrderDetail;

	public String excute() {
		try {
			currentUser = (UserCookie) session.get("userCookie");
			if (null == currentUser) {
				String url = "/b2c/pay.html";
				if(isNotNull(this.getOrderId())){
					url+="?b2cId="+ this.getB2cId();
				}
				if(isNotNull(this.getOrderId())){
					url+="&orderId="+ this.getOrderId();
				}
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
			//xinshuiOrderDetail = xinshuiService.loadXinshuiOrderDetail(this.getXinshuiOrderId());
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
	public String getB2cDetail() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser, expertId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				if(null!=orderId){
					params.put("orderId", orderId);
				}else{
					params.put("orderType", orderType);
				}
				params.put("userId", currentUser.getUserId());
				params.put("expertId", expertId);
				Map result=b2CService.loadDataInfo(params);
				result.put("buyUserName", currentUser.getUsername());
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
	 * 判断当前b2c产品用户是否已经购买
	 */
	public String isAlreadyBuyTheProduct() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser,expertId,orderType)) {
				Long result=0L;
				Map<String, Object> params = new HashMap<String, Object>();
				Long buyUserId=currentUser.getUserId();
				params.put("expertId", expertId);
				params.put("buyUserId", currentUser.getUserId());
				params.put("orderType", orderType);
				result = b2CService.isAlreadyBuyTheProduct(params);
						//: 2;//1.没有购买，允许购买 2.已经购买，不允许再购买
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
	public String confirmPay(){
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser, expertId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("expertId", expertId);
				params.put("buyUserId", currentUser.getUserId());
				params.put("useCaijin", isUseCaijin);
				params.put("orderType", orderType);
				params.put("buyUserName", currentUser.getUsername());
				Integer result=0;
				if(isNotNull(orderId)){
					params.put("orderId", orderId);	
					result=b2CService.b2cPayContinue(orderId);
				}else{
					result=b2CService.b2cPayAtFirst(params);
				}
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
			if (isNotNull(b2cId, currentUser, isUseCaijin)) {
				String result ="1"; //payService.myXinshuiPay(xinshuiId, currentUser
						//.getUserId(), isUseCaijin,currentUser.getUsername());
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

	public XinshuiOrderDetailVO getXinshuiOrderDetail() {
		return xinshuiOrderDetail;
	}

	public void setXinshuiOrderDetail(XinshuiOrderDetailVO xinshuiOrderDetail) {
		this.xinshuiOrderDetail = xinshuiOrderDetail;
	}

	public Long getB2cId() {
		return b2cId;
	}

	public void setB2cId(Long id) {
		b2cId = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
