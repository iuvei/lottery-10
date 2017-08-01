package com.wintv.lottery.index.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.index.service.IndexService;
import com.wintv.lottery.index.vo.BetOrderVO;
/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public class IndexAction extends BaseAction {
	private static final long serialVersionUID = 1546112324263736182L;
	@Autowired
	private IndexService indexService;
	private String betCategory;// 投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场 62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
	private String lotteryType;// 1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球
	private Long phaseId;//期次ID
	private static String[] BET_CATEGORY_ARRAY={"1","2","5","3"};
	public String excute() {
		return SUCCESS;
	}
	/**
	 * 获取热门搜索关键词
	 */
	public String findHotKeys() {
		try {
			List<String> result = indexService.findHotKeys();
			if(isNotNull(result)){
				generateResult(1, MSG_SUCCESS, result);
			}else{
				generateResult(0, MSG_FAILURE, "errors");
			}
		}catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 获取精选方案
	 */
	public String findJingXuanPlanList() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			// 投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场 62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
			//胜负14场精选方案
			queryParams.put("betCategory", "1");
			List<BetOrderVO> windrawPlanList = indexService.findJingXuanPlanList(queryParams);
			//任选九场精选方案
			queryParams.put("betCategory", "2");
			List<BetOrderVO> optionalPlanList = indexService.findJingXuanPlanList(queryParams);
			//足球单场精选方案
			queryParams.put("betCategory", "6");
			List<BetOrderVO> singlePlanList = indexService.findJingXuanPlanList(queryParams);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("windrawPlanList", windrawPlanList);
			result.put("optionalPlanList", optionalPlanList);
			result.put("singlePlanList", singlePlanList);
			if(isNotNull(result)){
				generateResult(1, MSG_SUCCESS, result);
			}else{
				generateResult(0, MSG_FAILURE, "errors");
			}
		}catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 获取首页面左边数据
	 */
	public String findLeftData() {
		try {
			Map<String, Object> result = indexService.leftData();
			if(isNotNull(result)){
				generateResult(1, MSG_SUCCESS, result);
			}else{
				generateResult(0, MSG_FAILURE, "errors");
			}
		}catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	

	/**
	 * 获取合买推荐方案
	 * 
	 */
	public String findHemaiRecommendedPlanList() {
		try {
			Map result = null;
			if (isNotNull(this.getPhaseId(),this.getLotteryType())) {
				String betcategory=BET_CATEGORY_ARRAY[Integer.parseInt(this.getLotteryType())-6];
				result = indexService.findHemaiRecommendedPlanList(this.getPhaseId(), betcategory);
				if (isNotNull(result)) {
					result.put("mulRemainTime", remainSeconds((String)result.get("deadline")));
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			}else{
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	//计算到达截至时间的剩余时间
	private Long remainSeconds(String deadline){
		if(isNotNull(deadline)&&""!=deadline){
			Date deadlineDate=DateUtil.parseDate(deadline,"yyyy-MM-dd HH:mm");
			//Date systemDate=DateUtil.add(new Date(),-2);
			Date systemDate=new Date();
			Long remainTime=deadlineDate.getTime()-systemDate.getTime();
			remainTime=remainTime>0L?remainTime:0L;
			remainTime=remainTime/1000;
			return remainTime;
		}else{
			return 0L;
		}
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	public Long getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
	public String getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	
}
