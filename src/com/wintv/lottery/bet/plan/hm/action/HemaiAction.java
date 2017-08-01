package com.wintv.lottery.bet.plan.hm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.service.CanyuBuyService;
import com.wintv.lottery.bet.service.EnhancementService;

/**
 * 合买-方案详情
 * 
 * @author Arix04 by 2010-04-06
 * 
 * @version 1.0.0
 */

public class HemaiAction extends BaseAction {

	private static final long serialVersionUID = -3120586067364998119L;

	@Autowired
	private CanyuBuyService canyuBuyService;
	@Autowired
	private BetService betService;
	@Autowired
	private EnhancementService enhancementService;
	
	private Integer pageSize;// 页面显示数据条数
	private Integer startRow;// 查询开始行
	
	private Long betId;//方案ID
	private Long phaseId;//期次ID
	private String betCategory;//投注彩种 1: 胜负14场 2:任9场  3:4场进球  5:6 场半全场  '61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球'
	private String subscribeCopys;//认购份数
	private String planCode;//方案编号
	private String categoryType;//购买彩票(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) 
	private String useCaijin;//是否使用彩金
	private String type;//单式=2，复式=4
	private Long floorCopys;//保底份数
	
	/**
	 * 参与购买明细列表
	 * 
	 * @return
	 */
	public String searchCanyuList() {
		try {
			Map params = new HashMap();
			params.put("betId", betId);
			params.put("startRow", startRow);
			params.put("pageSize", pageSize);
			
			Map result = new HashMap();
			result.put("totalCount", canyuBuyService.findParticipateSize(params));
			result.put("canyuList", canyuBuyService.findParticipateList(params));
			
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/***
	 * 我的认购记录列表
	 * 
	 * @return
	 */
	public String searchMyBuyList() {
		try {
			Map result = new HashMap();
			if(request.getSession().getAttribute("userCookie") != null) {
				result.put("myBuyList", canyuBuyService.findMyBuyList(((UserCookie)request.getSession().getAttribute("userCookie")).getUserId(),betId));
			}
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/***
	 * 立即购买
	 * 
	 * @return
	 */
	public String betCoop() {
		try {
			Map params = getParams();
			long result = betService.saveBetCoopOrder(params);
			if (isNotNull(result)) {
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

	/***
	 * 获取页面参数
	 * 
	 * @return
	 */
	public Map getParams() {
		Map params = new HashMap();
		params.put("sponsorBetId", betId);
		params.put("betUserid", ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId());
		params.put("betUsername", ((UserCookie)request.getSession().getAttribute("userCookie")).getUsername());
		params.put("betCategory", betCategory);
		params.put("subscribeCopys", subscribeCopys);
		params.put("planCode", planCode);
		params.put("categoryType", categoryType);
		params.put("useCaijin", useCaijin);
		params.put("type", type);
		return params;
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
	
	/**
	 * 保底操作
	 * 
	 * @return
	 */
	public String floorOperate() {
		try {
			int i = 0;
			if(request.getSession().getAttribute("userCookie") != null) {
				Long userId = ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId();
				i = enhancementService.floorBetOrder(userId, betId, floorCopys);
			}
			generateResult(1, MSG_SUCCESS, i);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 撤单操作
	 * 
	 */
	public String withdrawalOperate() {
		try {
			int i = 0;
			if(request.getSession().getAttribute("userCookie") != null) {
				Long userId = ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId();
				i = enhancementService.cancelSpOrder(userId, betId);
			}
			generateResult(1, MSG_SUCCESS, i);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
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

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public String getSubscribeCopys() {
		return subscribeCopys;
	}

	public void setSubscribeCopys(String subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getUseCaijin() {
		return useCaijin;
	}

	public void setUseCaijin(String useCaijin) {
		this.useCaijin = useCaijin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public Long getFloorCopys() {
		return floorCopys;
	}

	public void setFloorCopys(Long floorCopys) {
		this.floorCopys = floorCopys;
	}
}
