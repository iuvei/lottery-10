package com.wintv.lottery.personal.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.personal.vo.BetOrderVO;

public interface HistoryMilitaryDao extends BaseDao {
	public Map  findHistoryBetList(Map params)throws DaoException;
	/**
	 * 心水历史记录 2010-04-20 15:57
	 *
	 */
	public Map  findHistoryXinshuiList(Map params)throws DaoException;
	/**
	 * 他的历史战绩  2010-04-20 14:20
	 * 参数:
	 * userId 用户ID
	 * 返回：
	 * 数组String[]:
	 * 胜负14场:
	 *   发单中奖次数:String[0]
	 *   总奖金:String[1]
	 * 任选9场:
	 *   发单中奖次数:String[2]
	 *   总奖金:String[3]
	 * 单场足彩:
     *   发单中奖次数:String[4]
	 *   总奖金:String[5]
	 *   
	 * 6场半全场:
     *   发单中奖次数:String[6]
	 *   总奖金:String[7]
	 * 4场进球
     *   发单中奖次数:String[8]
	 *   总奖金:String[9]
	 */
	public String[] loadHistoryMilitary(Long userId)throws DaoException;
}
