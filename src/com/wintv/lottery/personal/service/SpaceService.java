package com.wintv.lottery.personal.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.personal.vo.SpaceVO;

public interface SpaceService {
	/**根据个人签名 2010-04-26 15:08**/
	public boolean  updateSignature(String newSignature,Long userId)throws DaoException;
	/**
	 * 本站热门心水推荐  2010-04-22 18:00
	 * 
	 **/
	public List findHotXinshui(Map params)throws DaoException;
	
	public List findHotPlanList(String betCategory)throws DaoException;
	/**点击当前投注  2010-04-15 09:31**/
	public Map findCurrentBet(Long userId)throws DaoException;
	/**
	 * 当前心水  2010-04-20 17:47
	 * 
	 **/
	public Map findCurXinshui(Long userId)throws DaoException;
	/**
	 * 心水历史记录 2010-04-20 15:57
	 *
	 */
	public Map  findHistoryXinshuiList(Map params)throws DaoException;
	public Map  findHistoryBetList(Map params)throws DaoException;
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
	/**点击首页**/
	public Map findHomeSpaceData(Long userId)throws DaoException;
	public SpaceVO findSpaceData(Long userId)throws DaoException;
	/**访问个人中心  2010-04-14 16:19**/
	public boolean visteSpace(Long userId,Long spaceUserId)throws DaoException;
}
