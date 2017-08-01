package com.wintv.lottery.bet.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.admin.bet.vo.PhaseVO;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.vo.BetOrderVO;

public interface EnhancementService {
	/**
	 * --网站购彩大厅单场期次列表 2010-05-06 13:49
	 *
	 */
	public PhaseVO  findCurSinglePhase()throws DaoException;
	/**
	 * --网站购彩大厅('6':胜负彩14场  任9场   '5':6场半全场   '3':4场进球)期次列表 2010-05-06 13:49
	 * @param category
	 * @return
	 * @throws DaoException
	 */
	public Map  findInAdvancePhaseMap(String category)throws DaoException;
	/**
	 * 更改投注内容  2010-04-22 10:05
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public boolean updateBetOrder(Map params)throws DaoException;
	/**
	 * 根据期次ID查询对阵列表
	 * 参数:
	 *  phaseId:期次ID
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException;
	/**获取发起订单 以便用于修改投注内容  2010-04-21 10:05
	 * 参数:
	 *   Long betOrderId:投注订单ID
	 *
	 * 返回值:
	 * long allSubscribeCopys:总共购买的份数
	 * BetOrder betOrder：方案订单
	 **/
	public Map loadOrderInfo(Long  betOrderId)throws DaoException;
	/**
	 * 稍后保存文件到数据库 2010-04-20 11;16
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public boolean uploadTxtPath(Map map)throws DaoException;
	public BetOrderVO loadBetOrder(Long betId)throws DaoException;
	/**
	 * -撤单   2010-04-16 17:57
	 * 返回值:   1.撤单成功  2.进度超度50%不能撤单  4 非法操作    -1报错
	 *
	 */
	 public int cancelSpOrder(long userId,long betId)throws DaoException;
	 /**
	   * 保底   2010-04-16 16:11
	   * 返回 4为非法操作   返回1 正常保底  2 保底份数太少 3保底份数太多  
	   *
	   */
	  public int floorBetOrder(long userId,long betId,long flooCopys)throws DaoException;
}
