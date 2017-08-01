package com.wintv.lottery.bet.plan.dg.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.service.CanyuBuyService;

/**
 * 代购-方案详情
 * 
 * @author Arix04 by 2010-04-06
 * 
 * @version 1.0.0
 */

public class DaigouAction extends BaseAction {

	private static final long serialVersionUID = -3120586067364998119L;

	@Autowired
	private CanyuBuyService canyuBuyService;
	@Autowired
	private BetService betService;
	
	private Long betId;//方案ID
	private Long phaseId;//期次ID
	private BetOrder betOrder;//方案详细信息
	private String stars;//战绩
	private Long zjCnt;//发起人中奖次数
	private Long myattentionCnt;//统计关注我的人数量
	private Long kingGDCnt;//统计金牌发起人的跟单总次数
	private String isKingSponsor;//发起人是否是金牌发起人
	private String allBonus;//发起人中奖金额
	private String kjNo;//开奖号码
	private String isGD;//是否是跟单用户：1 = 跟单用户、0 = 非跟单用户
	private String isEnd;//是否截止：1 = 已经截止、0 = 没有截止
	private String isAttention;//是否关注：1 = 已经关注、0 = 没有关注
	private List<AgainstVo> againstList;//对阵信息列表
	private String betCategory;	// 投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场
								// '61','单场半全场',
								// '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球'
	
	public String planDaigou() {
		try {
			Map params = null;
			if ("1".equals(betCategory)) {
				params = canyuBuyService.loadSingle14OrderInfo(betId);
			}
			if ("2".equals(betCategory)) {
				params = canyuBuyService.loadOptional9OrderInfo(betId);
			}
			if ("3".equals(betCategory)) {
				params = canyuBuyService.loadGoal4Info(betId);
			}
			if ("5".equals(betCategory)) {
				params = canyuBuyService.loadHalfComplete6Info(betId);
			}
			if ("61".equals(betCategory) || "62".equals(betCategory) || "63".equals(betCategory) || "64".equals(betCategory) || "65".equals(betCategory)) {
				params = canyuBuyService.loadBeijingOrderInfo(betId);
			}
			betOrder = (BetOrder)params.get("betOrder");
			
			//如果不是本人查看则转到500页面
			if(request.getSession().getAttribute("userCookie") == null || ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId().longValue() != betOrder.getBetUserid().longValue()) {
				return INPUT;
			}
			
			stars = (String)params.get("stars");
			allBonus = (String)params.get("allBonus");
			zjCnt = (Long)params.get("zjCnt");
			myattentionCnt = (Long)params.get("myattentionCnt");
			kingGDCnt = (Long)params.get("kingGDCnt");
			isKingSponsor = ((Long)params.get("isKingSponsor")).toString();
			kjNo = (String)params.get("kjNo");
			
			if(request.getSession().getAttribute("userCookie") != null) {
				isGD = canyuBuyService.isGD(betOrder.getBetUserid(), ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId())?"1":"0";
			} else {
				isGD = "0";
			}
			
			isEnd = betOrder.getEndTime().compareTo(new Date()) < 0?"1":"0";
			
			if(request.getSession().getAttribute("userCookie") != null) {
				isAttention = canyuBuyService.isAttention(((UserCookie)request.getSession().getAttribute("userCookie")).getUserId(), betOrder.getBetUserid()).toString();
			} else {
				isAttention = "0";
			}
			
			if(betCategory.indexOf("6") == -1) {
				Map p = new HashMap();
				p.put("phaseId", betOrder.getPhaseId());
				againstList = canyuBuyService.findAgainstList(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}

		return betOrder.getType() == "1"?"single":"duplex";
	}
	
	/**
	 * 获取足球单场方案信息
	 * @return
	 */
	public String loadPlanDetailList() {
		try {
			List<BetOrderChoice> planDetailList = canyuBuyService.loadMyBetOrderChoiceList(betId, phaseId);
			generateResult(1, MSG_SUCCESS, planDetailList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public BetService getBetService() {
		return betService;
	}

	public void setBetService(BetService betService) {
		this.betService = betService;
	}

	public BetOrder getBetOrder() {
		return betOrder;
	}

	public void setBetOrder(BetOrder betOrder) {
		this.betOrder = betOrder;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public Long getZjCnt() {
		return zjCnt;
	}

	public void setZjCnt(Long zjCnt) {
		this.zjCnt = zjCnt;
	}

	public Long getMyattentionCnt() {
		return myattentionCnt;
	}

	public void setMyattentionCnt(Long myattentionCnt) {
		this.myattentionCnt = myattentionCnt;
	}

	public Long getKingGDCnt() {
		return kingGDCnt;
	}

	public void setKingGDCnt(Long kingGDCnt) {
		this.kingGDCnt = kingGDCnt;
	}

	public String getIsKingSponsor() {
		return isKingSponsor;
	}

	public void setIsKingSponsor(String isKingSponsor) {
		this.isKingSponsor = isKingSponsor;
	}

	public String getAllBonus() {
		return allBonus;
	}

	public void setAllBonus(String allBonus) {
		this.allBonus = allBonus;
	}

	public String getKjNo() {
		return kjNo;
	}

	public void setKjNo(String kjNo) {
		this.kjNo = kjNo;
	}

	public String getIsGD() {
		return isGD;
	}

	public void setIsGD(String isGD) {
		this.isGD = isGD;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}

	public List<AgainstVo> getAgainstList() {
		return againstList;
	}

	public void setAgainstList(List<AgainstVo> againstList) {
		this.againstList = againstList;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
}
