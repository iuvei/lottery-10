package com.wintv.lottery.bet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.vo.PhaseNoVO;

/**
 * 足球单场-上下单双玩法
 * 
 * @author weiss
 * 
 * @version 1.0.0
 */

public class SingleUpDownAction extends BaseAction {
	private static final long serialVersionUID = -2752991770591556262L;
	@Autowired
	private BetService betService;
	
	private String phaseCategory = "1";//期次分类 '1':北京单场期次
	private String isCurrent = "1";//足彩期次是否是当前期次 '1':当前期次  '2':预售期次 3:历史期次
	private Long phaseId;//期次ID
	
	/***
	 * 获取让球胜平负列表
	 * @return
	 */
	public String searchSingleUpDownList() {
		
		try {
			Map params = new HashMap();
			if(isNotNull(phaseId)) {
				params.put("phaseId", phaseId);
			} else {
				params.put("phaseCategory", phaseCategory);
				params.put("isCurrent", isCurrent);
			}
			Map resultMap = betService.findPhaseAgainstList(params);
			generateResult(1, MSG_SUCCESS, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/***
	 * 获取期次列表
	 * @return
	 */
	public String searchLatestPhaseList() {
		try {
			Map params = new HashMap();
			params.put("phaseCategory", "1");
			List<PhaseNoVO> phaseNoVOList = betService.findLatestPhaseList(params);
			generateResult(1, MSG_SUCCESS, phaseNoVOList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	public String getPhaseCategory() {
		return phaseCategory;
	}

	public void setPhaseCategory(String phaseCategory) {
		this.phaseCategory = phaseCategory;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
}
