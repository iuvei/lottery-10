package com.wintv.lottery.bet.dao;

import java.util.List;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.personal.vo.BetOrderVO;

public interface BetHallDao extends BaseDao {
	/**足球单场 合买推荐方案(含置顶) 针对杨总的界面  2010-04-26 14:00**/
	public List<BetOrderVO> loadDanchangAll()throws DaoException;
	/**任选9场 购彩大厅  2010-04-25 13:40*/
	public List<BetOrderVO> loadRen9()throws DaoException;
	public List<BetOrderVO> loadWinLose14()throws DaoException;
	/**足球单场 合买推荐方案(含置顶)  2010-04-24 14:25*/
    public List<BetOrderVO> loadDanchang(String betCategory)throws DaoException;
}
