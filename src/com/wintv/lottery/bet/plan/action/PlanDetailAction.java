package com.wintv.lottery.bet.plan.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.bet.service.CanyuBuyService;

/**
 * 方案详情-请求转发
 * 
 * @author Arix04 by 2010-04-06
 * 
 * @version 1.0.0
 */

public class PlanDetailAction extends BaseAction {

	private static final long serialVersionUID = 2887812433728918297L;

	@Autowired
	private CanyuBuyService canyuBuyService;

	private String betCategory;// 投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场
								// '61','单场半全场',
								// '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球'
	private String betType;// 单式代购：1、单式合买：2、复式代购：3、复式合买：4
	private Long betId;// 方案ID

	public String planDetail() {
		String returnUrl = "";
		try {
			BetOrder betOrder = (BetOrder) canyuBuyService
					.findProperties(betId);
			betCategory = betOrder.getBetCategory();
			betType = betOrder.getType();
			
			if ("1".equals(betCategory)) {
				returnUrl = "windraw14";
			}
			if ("2".equals(betCategory)) {
				returnUrl = "optional9";
			}
			if ("3".equals(betCategory)) {
				returnUrl = "goal4";
			}
			if ("5".equals(betCategory)) {
				returnUrl = "halfComplete6";
			}
			if ("61".equals(betCategory) || "62".equals(betCategory) || "63".equals(betCategory) || "64".equals(betCategory) || "65".equals(betCategory)) {
				returnUrl = "single";
			}
			if ("2".equals(betType)) {
				returnUrl += "Single";
			}
			if ("4".equals(betType)) {
				returnUrl += "Duplex";
			}
			if ("1".equals(betType) || "3".equals(betType)) {
				returnUrl = "planDaigou";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return returnUrl;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

}
