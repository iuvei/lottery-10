package com.wintv.lottery.index.action;

import java.util.Collection;
import java.util.List;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetHotSearch;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.service.HallService;
import com.wintv.lottery.index.vo.InGoalBetScaleVo;
import com.wintv.lottery.personal.vo.BetOrderVO;

@SuppressWarnings("serial")
public class BetIndexAction  extends BaseAction {
	
	
	public void hot_search(){
		List<BetHotSearch>  list = betService.hot_search();
		//JSONArray json_list = new JSONArray();
		//JSONObject obj = null;
		StringBuilder sb = new StringBuilder();
		for(BetHotSearch bts : list){
			//obj = new JSONObject();
			//obj.put("username", bts.getUsername());
			sb.append(bts.getUsername() +"~|");
		}
		ajaxForAction(sb.toString());
	}
	
	
	//单场 胜负14 精选合买 
	public String getSingleHM(){
		try {
			list_bo = hallService.loadDanchangList(betCategory);
			if(null != list_bo)
				generateResult(0, "success", list_bo);
			else
				generateResult(0, "false", null);
		} catch (DaoException e) {
			e.printStackTrace();
			generateResult(0, "false", null);
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	//进球彩 上期冷门
	public String getDarkRank(){
		List<InGoalBetScaleVo> list = betService.getDarkRank(phase);
		if(null != list)
			generateResult(0, "success", list);
		else
			generateResult(0, "false", list);
		return SUCCESS;
	}
	
	
	//进球彩 投注比例查询
	public String getCurBetScale(){
		List<InGoalBetScaleVo> list = betService.getCurBetScale(phase);
		if(null != list)
			generateResult(0, "success", list);
		else
			generateResult(0, "false", list);
		return SUCCESS;
	}
	
	
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private BetService betService;
	
	private Collection<BetOrderVO> list_bo;
	private String betCategory;
	private String phase;
	
	public Collection<BetOrderVO> getList_bo() {
		return list_bo;
	}
	public void setList_bo(Collection<BetOrderVO> list_bo) {
		this.list_bo = list_bo;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}

}
