package com.wintv.lottery.bet.plan.hm.action;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.bet.service.CanyuBuyService;


/**
 * 足球单场-方案详情-单式
 * 
 * @author Arix04 by 2010-04-06
 * 
 * @version 1.0.0
 */

public class SingleSingleAction extends BaseAction {

	private static final long serialVersionUID = 1471158720366732369L;

	@Autowired
	private CanyuBuyService canyuBuyService;

	private Long betId;//方案ID
	private BetOrder betOrder;//方案详细信息
	private String stars;//战绩
	private Long zjCnt;//发起人中奖次数
	private Long myattentionCnt;//统计关注我的人数量
	private Long kingGDCnt;//统计金牌发起人的跟单总次数
	private String isKingSponsor;//发起人是否是金牌发起人
	private String allBonus;//发起人中奖金额
	private String planStatus;//方案状态：1 = 满员、2 = 未满员、 3 = 已撤单
	private String kjNo;//开奖号码
	private String isGD;//是否是跟单用户：1 = 跟单用户、0 = 非跟单用户
	private String isEnd;//是否截止：1 = 已经截止、0 = 没有截止
	private String isAttention;//是否关注：1 = 已经关注、0 = 没有关注
	
	public String singleSingle() {
		try {
			Map params = canyuBuyService.loadBeijingOrderInfo(betId);
			betOrder = (BetOrder) params.get("betOrder");
			stars = (String) params.get("stars");
			zjCnt = (Long) params.get("zjCnt");
			allBonus = (String)params.get("allBonus");
			myattentionCnt = (Long)params.get("myattentionCnt");
			kingGDCnt = (Long)params.get("kingGDCnt");
			isKingSponsor = ((Long)params.get("isKingSponsor")).toString();
			kjNo = (String)params.get("kjNo");
			planStatus = (String)params.get("planStatus");
			
			if(request.getSession().getAttribute("userCookie") != null) {
				isGD = canyuBuyService.isGD(betId, ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId())?"1":"0";
			} else {
				isGD = "0";
			}
			
			isEnd = betOrder.getEndTime().compareTo(new Date()) < 0?"1":"0";
			
			if(request.getSession().getAttribute("userCookie") != null) {
				isAttention = canyuBuyService.isAttention(((UserCookie)request.getSession().getAttribute("userCookie")).getUserId(), betOrder.getBetUserid()).toString();
			} else {
				isAttention = "0";
			}
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

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
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
}
