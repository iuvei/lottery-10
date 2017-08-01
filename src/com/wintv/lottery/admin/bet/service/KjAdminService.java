package com.wintv.lottery.admin.bet.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.bet.vo.BonusGov;

public interface KjAdminService {
	/**
	 * 审核
	 * 参数：
	 *  id:主键
	 */
	public boolean updateBonusGov(Long id)throws DaoException;
	/**
	 *参数:
	 * lotteryType:'1':胜负14场、'2':任选9场、'3':4 场进球 '5':6 场半全场
	 * from:起始时间
	 * to:结束时间
	 * 
	 */
  public long findBonusGovSize(Map params)throws DaoException;
  /**
	 *参数:
	 * lotteryType:'1':胜负14场、'2':任选9场、'3':4 场进球 '5':6 场半全场
	 * from:起始时间
	 * to:结束时间
	 * 
	 */
public List<BonusGov> findBonusGovList(Map params)throws DaoException;
/**开奖期次列表 2010-04-28 17:58
 *参数：
 *category:
 *  1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     
 *  61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
 **/
public List<String>  findPhaseList(String  category)throws DaoException;

}
