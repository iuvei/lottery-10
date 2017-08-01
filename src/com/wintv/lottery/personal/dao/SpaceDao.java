package com.wintv.lottery.personal.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.personal.vo.SpaceVO;

@SuppressWarnings("unchecked")
public interface SpaceDao extends BaseDao {
	/**根据个人签名 2010-04-26 15:08**/
	public boolean  updateSignature(String newSignature,Long userId)throws DaoException;
	/**
	 * 本站热门心水推荐  2010-04-22 18:00
	 * 
	 **/
	public List findHotXinshui(Map params)throws DaoException;
	/**点击当前投注  2010-04-15 09:31**/
	public Map findCurrentBet(Long userId)throws DaoException;
	/**
	 * 当前心水  2010-04-20 17:47
	 * 
	 **/
	public Map findCurXinshui(Long userId)throws DaoException;
	/**本站热门方案推荐  2010-04-15 10:01**/
	public List findHotPlanList(String betCategory)throws DaoException;
	/**点击首页**/
	public Map findHomeSpaceData(Long userId)throws DaoException;
	public SpaceVO findSpaceData(Long userId)throws DaoException;
	/**访问个人中心  2010-04-14 16:19**/
	public boolean visteSpace(Long userId,Long spaceUserId)throws DaoException;
}
