package com.wintv.lottery.pay.action;

import java.math.BigDecimal;
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

@SuppressWarnings("unchecked")
public class ChargeAction extends BaseAction {
	private static final long serialVersionUID = 7362092892948427678L;

	@Autowired
	private PayService payService;
	
	private BigDecimal money;
	private BankVo bank;
	private List<BankVo> bankVoList;
	private String bankName;
	private String bankCode;
	private String imgUrl;
	private String bankUrl;
	
	/**
	 * 跳转到我要充值页面
	 * @return
	 */
	public String charge() {
		try {
			Long userid = ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId();
			//获取上一次取款银行信息
			bank = payService.findLastChargeBank(userid);
			bankVoList = payService.findChargeBank();
			
			//银行列表中去除推荐银行，避免重复
			Iterator it = bankVoList.iterator();
			while(it.hasNext()){
				BankVo bankTemp = (BankVo)it.next();
				if(bankTemp.getBankCode().equals(bank.getBankCode())) {
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
	 * 我要充值第二步
	 * @return
	 */
	public String chargeStep2() {
		if(money == null) {
			request.setAttribute("errorCode", 1);//充值金额不能为空
			return "fail";
		}
		if(money.compareTo(new BigDecimal(2)) < 0) {
			request.setAttribute("errorCode", 2);//充值金额不能小于2元
			return "fail";
		}
		return SUCCESS;
	}
	
	/**
	 * 确认充值金额提交到网银支付页面
	 * @return
	 */
	public String chargeConfirm() {
		return SUCCESS;
	}
	
	/**
	 * 充值完成返回的action
	 * @return
	 */
	public String chargeComplete() {
		try {
			Map params = new HashMap();
			payService.onlinePay(params);
			//取得订单状态返回值
			Long userid = Long.parseLong((String)request.getAttribute("userid"));
			VirtualAccount virtualAccount = payService.findVirtualAccount(userid);
			request.getSession().setAttribute("virtualAccount", virtualAccount);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**----------------------------------------get&set----------------------------------------**/
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getBankUrl() {
		return bankUrl;
	}

	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
