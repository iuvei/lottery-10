package com.wintv.lottery.bet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.BetService;

/**
 * 足球单场-比分玩法
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
public class SingleOddsAction extends BaseAction {
	private static final long serialVersionUID = -8494212456025919965L;
	private Long phaseId;// 期次Id
	private String isCurrent;//是否是当前期次
	@Autowired
	private BetService betService;
	/**
	 * 获取半全场对阵比赛赛事列表
	 * 
	 */
	public String getPhaseAgainstRacesList() {
		try {
			Map result = null;
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("phaseCategory","1");
			if (isNotNull(isCurrent)) {
				params.put("isCurrent", isCurrent);
			}else if (isNotNull(phaseId)) {
				params.put("phaseId",phaseId);
				params.put("isCurrent", "0");
			}
			result = betService.findPhaseAgainstList(params);
			if (isNotNull(result)) {
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
	 * 最近15期的期次列表
	 */
	public String getLatestPhaseList() {
		  try { 
			  List result = null; 
			  Map<String,Object> params=new HashMap<String,Object>();
			  params.put("phaseCategory","1"); 
			  result = betService.findLatestPhaseList(params); 
			  if (isNotNull(result)) {
				generateResult(1, MSG_SUCCESS, result); } else { generateResult(0,
						MSG_FAILURE, "errors"); } 
		  } catch (Exception e) { 
			  generateResult(0,MSG_FAILURE, "errors"); 
			  e.printStackTrace(); 
		   throw new LotteryBizException(e.getLocalizedMessage()); 
		  }
		return SUCCESS;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
   
}
