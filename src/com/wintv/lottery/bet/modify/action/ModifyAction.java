package com.wintv.lottery.bet.modify.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.service.CanyuBuyService;
import com.wintv.lottery.bet.service.EnhancementService;

/**
 * 方案修改-操作页面
 * 
 * @author Arix04 by 2010-04-20
 * 
 * @version 1.0.0
 */

public class ModifyAction extends BaseAction {
	
	private static final long serialVersionUID = -5844377692401550511L;
	
	@Autowired
	private CanyuBuyService canyuBuyService;
	@Autowired
	private EnhancementService enhancementService;
	
	private Long betId;//方案ID
	private Long phaseId;//期次ID
	private Long allSubscribeCopys;//发起人认购总份数
	private BetOrder betOrder;//方案信息
	private List<AgainstVo> againstList;//对阵信息列表
	private String mdType;//1 = 提交、 2 = 修改
	private String betCategory;//彩种 1,2,3,5
	private String plan;//投注内容
	
	/***
	 * 各个玩法方案详情修改页面
	 * @return
	 */
	public String modify() {
		try {
			Map result = enhancementService.loadOrderInfo(betId);
			betOrder = (BetOrder)result.get("betOrder");
			allSubscribeCopys = (Long)result.get("allSubscribeCopys");
			
			if("1".equals(betOrder.getBetCategory())) {
				return "windraw14";
			}
			if("2".equals(betOrder.getBetCategory())) {
				return "optional9";	
			}
			if("5".equals(betOrder.getBetCategory())) {
				return "halfComplete6";
			}
			if("3".equals(betOrder.getBetCategory())) {
				return "goal4";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/***
	 * ajax获取对阵信息
	 * 
	 * @return
	 */
	public String loadAgainstList() {
		try {
			Map p = new HashMap();
			p.put("phaseId", phaseId);
			againstList = enhancementService.findAgainstList(p);
			generateResult(1, MSG_SUCCESS, againstList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/***
	 * 修改确认
	 * 
	 * @return
	 */
	public String modifyConfirm() {
		try {
			boolean flg = true;
			if(request.getSession().getAttribute("userCookie") != null) {
				Long userId = ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId();
				Map m = new HashMap();
				m.put("userId", userId);
				m.put("betCategory", betCategory);
				m.put("betId", betId);
				m.put("plan", plan);
				System.out.println(plan);
				flg = enhancementService.updateBetOrder(m);
			} else {
				flg = false;
			}
			generateResult(1, MSG_SUCCESS, flg);
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	public String getMdType() {
		return mdType;
	}

	public void setMdType(String mdType) {
		this.mdType = mdType;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public BetOrder getBetOrder() {
		return betOrder;
	}

	public void setBetOrder(BetOrder betOrder) {
		this.betOrder = betOrder;
	}

	public List<AgainstVo> getAgainstList() {
		return againstList;
	}

	public void setAgainstList(List<AgainstVo> againstList) {
		this.againstList = againstList;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
	
	public Long getAllSubscribeCopys() {
		return allSubscribeCopys;
	}

	public void setAllSubscribeCopys(Long allSubscribeCopys) {
		this.allSubscribeCopys = allSubscribeCopys;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}
}
