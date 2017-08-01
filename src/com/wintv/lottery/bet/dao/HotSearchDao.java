package com.wintv.lottery.bet.dao;

import java.util.List;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetHotSearch;
import com.wintv.lottery.bet.vo.HotSearchVO;

public interface HotSearchDao extends BaseDao<BetHotSearch,Long> {
	public boolean   saveHotSponsors(List<HotSearchVO> list)throws DaoException;
	/**热门搜索金牌发起人列表  2010-03-26 13:55**/
	public List<HotSearchVO>   findHotOrders(Long phaseId)throws DaoException;

}
