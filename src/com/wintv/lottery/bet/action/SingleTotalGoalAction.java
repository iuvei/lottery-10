package com.wintv.lottery.bet.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.BetService;

/**
 * 足球单场-总进球数玩法
 * 
 * @author Arix04 by 2010-03-05
 * 
 * @version 1.0.0
 */
public class SingleTotalGoalAction extends BaseAction {
	private static final long serialVersionUID = -3361384831673624079L;
	
	@Autowired
	private BetService betService;
	
	private String phaseCategory = "1";//期次分类 '1':北京单场期次
	private String isCurrent = "1";//足彩期次是否是当前期次 '1':当前期次  '2':预售期次 3:历史期次
	private Long phaseId;//期次ID
	
}
