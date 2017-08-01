package com.wintv.lottery.mycenter.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.HallService;
import com.wintv.lottery.personal.vo.BetOrderVO;

/**
 * 购彩大厅
 * 
 * @author Arix04 by 2010-04-22
 * 
 * @version 1.0.0
 */

public class HallAction extends BaseAction {

	private static final long serialVersionUID = -8148262812871995731L;

	@Autowired
	private HallService hallService;
	
	private List<BetOrderVO> windraw14List;
	private List<BetOrderVO> optional9List;
	private List<BetOrderVO> singleList;
	
	public String excute() {
		return null;
	}
	
	public String hall() {
		return SUCCESS;
	}
	
	/***
	 * 加载胜负14场列表
	 * @return
	 */
	public String loadWindraw14List() {
		try {
			windraw14List = hallService.loadWinLose14List();
			generateResult(1, MSG_SUCCESS, windraw14List);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/***
	 * 加载任选9场列表
	 * @return
	 */
	public String loadOptional9List() {
		try {
			optional9List = hallService.loadloadRen9List();
			generateResult(1, MSG_SUCCESS, optional9List);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/***
	 * 加载足球单场列表
	 * @return
	 */
	public String loadSingleList() {
		try {
			singleList = hallService.loadDanchangAll();
			generateResult(1, MSG_SUCCESS, singleList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	public List<BetOrderVO> getWindraw14List() {
		return windraw14List;
	}

	public void setWindraw14List(List<BetOrderVO> windraw14List) {
		this.windraw14List = windraw14List;
	}

	public List<BetOrderVO> getOptional9List() {
		return optional9List;
	}

	public void setOptional9List(List<BetOrderVO> optional9List) {
		this.optional9List = optional9List;
	}

	public List<BetOrderVO> getSingleList() {
		return singleList;
	}

	public void setSingleList(List<BetOrderVO> singleList) {
		this.singleList = singleList;
	}
}
