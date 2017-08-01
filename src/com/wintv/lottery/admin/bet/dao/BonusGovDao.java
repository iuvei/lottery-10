package com.wintv.lottery.admin.bet.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.bet.vo.BonusGov;


public interface BonusGovDao extends BaseDao{
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

}
