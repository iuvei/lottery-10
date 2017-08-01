package com.wintv.lottery.personal.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wintv.framework.exception.DaoException;

import com.wintv.lottery.personal.dao.HistoryMilitaryDao;
import com.wintv.lottery.personal.dao.SpaceDao;
import com.wintv.lottery.personal.service.SpaceService;
import com.wintv.lottery.personal.vo.SpaceVO;

@Service("spaceService")
public class SpaceServiceImpl implements SpaceService{
	/**根据个人签名 2010-04-26 15:08**/
	public boolean  updateSignature(String newSignature,Long userId)throws DaoException{
		return this.spaceDao.updateSignature(newSignature, userId);
	}
	/**
	 * 本站热门心水推荐  2010-04-22 18:00
	 * 
	 **/
	public List findHotXinshui(Map params)throws DaoException{
		return this.spaceDao.findHotXinshui(params);
	}
	/**本站热门方案推荐  2010-04-15 10:01**/
	public List findHotPlanList(String betCategory)throws DaoException{
		return this.spaceDao.findHotPlanList(betCategory);
	}
	/**点击当前投注  2010-04-15 09:31**/
	public Map findCurrentBet(Long userId)throws DaoException{
		return this.spaceDao.findCurrentBet(userId);
	}
	/**
	 * 当前心水  2010-04-20 17:47
	 * 
	 **/
	public Map findCurXinshui(Long userId)throws DaoException{
		return this.spaceDao.findCurXinshui(userId);
	}
	/**
	 * 心水历史记录 2010-04-20 15:57
	 * 参数：
	 *  userId 用户ID
	 *  betCategory:彩种
	 *  startRow 起始记录 从1开赛
	 *  pageSize 每一页显示的最大记录数
	 */
	public Map  findHistoryXinshuiList(Map params)throws DaoException{
		return this.hisMiliDao.findHistoryXinshuiList(params);
	}
	/**
	 *历史发起投注 2010-04-20 16:54
	 *参数:
	 *  userId 用户ID
	 *  betCategory:彩种
	 *  startRow 起始记录 从1开赛
	 *  pageSize 每一页显示的最大记录数
	 */
	public Map  findHistoryBetList(Map params)throws DaoException{
		return this.hisMiliDao.findHistoryBetList(params);
	}
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
	public String[] loadHistoryMilitary(Long userId)throws DaoException{
		return hisMiliDao.loadHistoryMilitary(userId);
	}
	public SpaceVO findSpaceData(Long userId)throws DaoException{
		return this.spaceDao.findSpaceData(userId);
	}
	/**点击首页**/
	public Map findHomeSpaceData(Long userId)throws DaoException{
		return this.spaceDao.findHomeSpaceData(userId);
	}
	/**访问个人中心  2010-04-14 16:19**/
	public boolean visteSpace(Long userId,Long spaceUserId)throws DaoException{
		return this.spaceDao.visteSpace(userId, spaceUserId);
	}
	@Resource
	private SpaceDao  spaceDao;
	@Resource
	private HistoryMilitaryDao hisMiliDao;

}
